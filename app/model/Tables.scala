package model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Department.schema ++ Employee.schema ++ EmployeeDetail.schema ++ Password.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Department
   *  @param code Database column code SqlType(VARCHAR), PrimaryKey, Length(255,true)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true) */
  case class DepartmentRow(code: String, name: String)
  /** GetResult implicit for fetching DepartmentRow objects using plain SQL queries */
  implicit def GetResultDepartmentRow(implicit e0: GR[String]): GR[DepartmentRow] = GR{
    prs => import prs._
    DepartmentRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table department. Objects of this class serve as prototypes for rows in queries. */
  class Department(_tableTag: Tag) extends profile.api.Table[DepartmentRow](_tableTag, Some("playsample"), "department") {
    def * = (code, name) <> (DepartmentRow.tupled, DepartmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(code), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> DepartmentRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column code SqlType(VARCHAR), PrimaryKey, Length(255,true) */
    val code: Rep[String] = column[String]("code", O.PrimaryKey, O.Length(255,varying=true))
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Department */
  lazy val Department = new TableQuery(tag => new Department(tag))

  /** Entity class storing rows of table Employee
   *  @param employeeNumber Database column employee_number SqlType(INT), PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param kana Database column kana SqlType(VARCHAR), Length(255,true)
   *  @param mailAddress Database column mail_address SqlType(VARCHAR), Length(255,true)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP) */
  case class EmployeeRow(employeeNumber: Int, name: String, kana: String, mailAddress: String, updateDate: java.sql.Timestamp)
  /** GetResult implicit for fetching EmployeeRow objects using plain SQL queries */
  implicit def GetResultEmployeeRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[EmployeeRow] = GR{
    prs => import prs._
    EmployeeRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table employee. Objects of this class serve as prototypes for rows in queries. */
  class Employee(_tableTag: Tag) extends profile.api.Table[EmployeeRow](_tableTag, Some("playsample"), "employee") {
    def * = (employeeNumber, name, kana, mailAddress, updateDate) <> (EmployeeRow.tupled, EmployeeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(employeeNumber), Rep.Some(name), Rep.Some(kana), Rep.Some(mailAddress), Rep.Some(updateDate)).shaped.<>({r=>import r._; _1.map(_=> EmployeeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column employee_number SqlType(INT), PrimaryKey */
    val employeeNumber: Rep[Int] = column[Int]("employee_number", O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column kana SqlType(VARCHAR), Length(255,true) */
    val kana: Rep[String] = column[String]("kana", O.Length(255,varying=true))
    /** Database column mail_address SqlType(VARCHAR), Length(255,true) */
    val mailAddress: Rep[String] = column[String]("mail_address", O.Length(255,varying=true))
    /** Database column update_date SqlType(TIMESTAMP) */
    val updateDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("update_date")
  }
  /** Collection-like TableQuery object for table Employee */
  lazy val Employee = new TableQuery(tag => new Employee(tag))

  /** Entity class storing rows of table EmployeeDetail
   *  @param employeeNumber Database column employee_number SqlType(INT), PrimaryKey
   *  @param age Database column age SqlType(INT)
   *  @param gender Database column gender SqlType(VARCHAR), Length(255,true)
   *  @param zip Database column zip SqlType(VARCHAR), Length(255,true)
   *  @param address Database column address SqlType(VARCHAR), Length(255,true) */
  case class EmployeeDetailRow(employeeNumber: Int, age: Int, gender: String, zip: String, address: String)
  /** GetResult implicit for fetching EmployeeDetailRow objects using plain SQL queries */
  implicit def GetResultEmployeeDetailRow(implicit e0: GR[Int], e1: GR[String]): GR[EmployeeDetailRow] = GR{
    prs => import prs._
    EmployeeDetailRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table employee_detail. Objects of this class serve as prototypes for rows in queries. */
  class EmployeeDetail(_tableTag: Tag) extends profile.api.Table[EmployeeDetailRow](_tableTag, Some("playsample"), "employee_detail") {
    def * = (employeeNumber, age, gender, zip, address) <> (EmployeeDetailRow.tupled, EmployeeDetailRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(employeeNumber), Rep.Some(age), Rep.Some(gender), Rep.Some(zip), Rep.Some(address)).shaped.<>({r=>import r._; _1.map(_=> EmployeeDetailRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column employee_number SqlType(INT), PrimaryKey */
    val employeeNumber: Rep[Int] = column[Int]("employee_number", O.PrimaryKey)
    /** Database column age SqlType(INT) */
    val age: Rep[Int] = column[Int]("age")
    /** Database column gender SqlType(VARCHAR), Length(255,true) */
    val gender: Rep[String] = column[String]("gender", O.Length(255,varying=true))
    /** Database column zip SqlType(VARCHAR), Length(255,true) */
    val zip: Rep[String] = column[String]("zip", O.Length(255,varying=true))
    /** Database column address SqlType(VARCHAR), Length(255,true) */
    val address: Rep[String] = column[String]("address", O.Length(255,varying=true))

    /** Foreign key referencing Employee (database name employee_detail_ibfk_1) */
    lazy val employeeFk = foreignKey("employee_detail_ibfk_1", employeeNumber, Employee)(r => r.employeeNumber, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table EmployeeDetail */
  lazy val EmployeeDetail = new TableQuery(tag => new EmployeeDetail(tag))

  /** Entity class storing rows of table Password
   *  @param employeeNumber Database column employee_number SqlType(INT), PrimaryKey
   *  @param password Database column password SqlType(VARCHAR), Length(255,true)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP) */
  case class PasswordRow(employeeNumber: Int, password: String, updateDate: java.sql.Timestamp)
  /** GetResult implicit for fetching PasswordRow objects using plain SQL queries */
  implicit def GetResultPasswordRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[PasswordRow] = GR{
    prs => import prs._
    PasswordRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table password. Objects of this class serve as prototypes for rows in queries. */
  class Password(_tableTag: Tag) extends profile.api.Table[PasswordRow](_tableTag, Some("playsample"), "password") {
    def * = (employeeNumber, password, updateDate) <> (PasswordRow.tupled, PasswordRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(employeeNumber), Rep.Some(password), Rep.Some(updateDate)).shaped.<>({r=>import r._; _1.map(_=> PasswordRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column employee_number SqlType(INT), PrimaryKey */
    val employeeNumber: Rep[Int] = column[Int]("employee_number", O.PrimaryKey)
    /** Database column password SqlType(VARCHAR), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column update_date SqlType(TIMESTAMP) */
    val updateDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("update_date")

    /** Foreign key referencing Employee (database name password_ibfk_1) */
    lazy val employeeFk = foreignKey("password_ibfk_1", employeeNumber, Employee)(r => r.employeeNumber, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Password */
  lazy val Password = new TableQuery(tag => new Password(tag))
}
