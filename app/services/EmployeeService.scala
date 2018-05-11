package services

import javax.inject.Inject

import akka.Done
import exceptions.OriginalRuntimeException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global

class MasterAlreadyExistException extends OriginalRuntimeException

class MasterNotExistException extends OriginalRuntimeException

class EmployeeService @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  import model.Tables._

  def create(employeeRow: EmployeeRow) = {
    (for {
      _ <- validateBeforeCreate(employeeRow.employeeNumber)
      count <- Employee += employeeRow
    } yield count).transactionally
  }

  def findAll() = Employee.result

  def validateBeforeCreate(employeeNumber: Int) = {
    Employee.filter(_.employeeNumber === employeeNumber).exists.result.flatMap { isExist =>
      if (isExist) {
        DBIO.failed(new MasterAlreadyExistException)
      } else {
        DBIO.successful(Done)
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
