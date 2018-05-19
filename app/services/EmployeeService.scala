package services

import akka.Done
import exceptions.{MasterAlreadyExistException, MasterNotExistException}
import io.github.nremond.SecureHash
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global

class EmployeeService @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  import model.Tables._

  def findAll() = Employee.result

  def create(employeeRow: EmployeeRow) = {
    (for {
      _ <- validateBeforeCreate(employeeRow.employeeNumber)
      count <- Employee += employeeRow.copy(password = SecureHash.createHash(employeeRow.password))
    } yield count).transactionally
  }


  def validateBeforeCreate(employeeNumber: Int) = {
    Employee.filter(_.employeeNumber === employeeNumber).exists.result.flatMap { isExist =>
      if (isExist) {
        DBIO.failed(new MasterAlreadyExistException)
      } else {
        DBIO.successful(Done)
      }
    }
  }

  def edit(employeeRow: EmployeeRow) = {
    (for {
      password <- validateBeforeEdit(employeeRow.employeeNumber)
      _ <- password.flatMap { r =>
        if (employeeRow.password.isEmpty) Employee.filter(_.employeeNumber === employeeRow.employeeNumber).update(employeeRow.copy(password = r))
        else Employee.filter(_.employeeNumber === employeeRow.employeeNumber).update(employeeRow.copy(password = SecureHash.createHash(employeeRow.password)))
      }
    } yield ()).transactionally
  }

  def validateBeforeEdit(employeeNumber: Int) = {
    Employee.filter(_.employeeNumber === employeeNumber).result.headOption.map { r =>
      if (r.isDefined) {
        DBIO.successful(r.get.password)
      } else {
        DBIO.failed(new MasterNotExistException)
      }
    }
  }

  def delete(employeeNumber: Int) = {
    (for {
      deleteEmployeeName <- validateBeforeDelete(employeeNumber)
      _ <- Employee.filter(_.employeeNumber === employeeNumber).delete
    } yield deleteEmployeeName).transactionally
  }

  def validateBeforeDelete(employeeNumber: Int) = {
    Employee.filter(_.employeeNumber === employeeNumber).map(_.name).result.headOption.flatMap { name =>
      if (name.isDefined) {
        DBIO.successful(name.get)
      } else {
        DBIO.failed(new MasterNotExistException)
      }
    }
  }

}
