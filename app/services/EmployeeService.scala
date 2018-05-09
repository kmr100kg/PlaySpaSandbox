package services

import javax.inject.Inject

import akka.Done
import exceptions.OriginalRuntimeException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global

class MasterAlreadyExistException extends OriginalRuntimeException

class EmployeeService @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  import model.Tables._

  def create(employeeRow: EmployeeRow) = {
    (for {
      _ <- validate(employeeRow.employeeNumber)
      count <- Employee += employeeRow
    } yield count).transactionally
  }

  def findAll() = Employee.result

  def validate(employeeNumber: Int) = {
    Employee.filter(_.employeeNumber === employeeNumber).exists.result.flatMap { isExist =>
      if (isExist) {
        DBIO.failed(new MasterAlreadyExistException)
      } else {
        DBIO.successful(Done)
      }
    }
  }

}
