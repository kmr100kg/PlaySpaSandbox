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

  def findBeforeCreate() = {
    for {
      nextEmployeeNumber <- Employee.map(_.employeeNumber).max.result
    } yield nextEmployeeNumber.getOrElse(0) + 1
  }

  def create(employeeRow: EmployeeRow, passwordRow: PasswordRow) = {
    (for {
      _ <- validateBeforeCreate(employeeRow.employeeNumber)
      _ <- Employee += employeeRow
      _ <- Password += passwordRow
    } yield ()).transactionally
  }


  def validateBeforeCreate(employeeNumber: Int) = {
    Employee.filter(_.employeeNumber === employeeNumber).exists.result.flatMap { isExist =>
      if (isExist) DBIO.failed(new MasterAlreadyExistException)
      else DBIO.successful(Done)
    }
  }

  def edit(employeeRow: EmployeeRow, passwordRow: PasswordRow) = {
    (for {
      _ <- validateBeforeEdit(employeeRow)
      _ <- {
        // パスワードが変更されていれば更新する
        if (passwordRow.password != "") {
          Password.filter(_.employeeNumber === employeeRow.employeeNumber).update(passwordRow)
        }
        val updateDate = Timestamp.valueOf(LocalDateTime.now())
        Employee.filter(_.employeeNumber === employeeRow.employeeNumber).update(employeeRow.copy(updateDate = updateDate))
      }
    } yield ()).transactionally
  }

  def validateBeforeEdit(employeeRow: EmployeeRow) = {
    Employee.filter(_.employeeNumber === employeeRow.employeeNumber).result.headOption.map { r =>
      if (r.isDefined) {
        if (r.get.updateDate.equals(employeeRow.updateDate)) DBIO.successful(Done)
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
