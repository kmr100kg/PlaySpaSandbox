package services

import java.sql.Timestamp
import java.time.LocalDateTime

import akka.Done
import io.github.nremond.SecureHash
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import exceptions.{MasterAlreadyExistException, MasterNotExistException, OptimisticLockException}

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
      if (isExist) DBIO.failed(new MasterAlreadyExistException)
      else DBIO.successful(Done)
    }
  }

  def edit(employeeRow: EmployeeRow) = {
    (for {
      password <- validateBeforeEdit(employeeRow)
      _ <- password.flatMap { r =>
        val updateDate = Timestamp.valueOf(LocalDateTime.now())
        if (employeeRow.password.isEmpty) Employee
          .filter(_.employeeNumber === employeeRow.employeeNumber)
          .update(employeeRow.copy(password = r, updateDate = updateDate))
        else Employee.filter(_.employeeNumber === employeeRow.employeeNumber)
          .update(employeeRow.copy(password = SecureHash.createHash(employeeRow.password), updateDate = updateDate))
      }
    } yield ()).transactionally
  }

  def validateBeforeEdit(employeeRow: EmployeeRow) = {
    Employee.filter(_.employeeNumber === employeeRow.employeeNumber).result.headOption.map { r =>
      if (r.isDefined) {
        if (r.get.updateDate.equals(employeeRow.updateDate)) DBIO.successful(r.get.password)
        else DBIO.failed(new OptimisticLockException)
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
