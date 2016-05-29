package by.slesh.bntu.imaster.persistence

import java.sql.{SQLException, Date}
import java.text.SimpleDateFormat

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import slick.driver.H2Driver.api._

/**
  * @author yauheni.putsykovich
  */

object DatabaseSource {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  private val logger = LoggerFactory.getLogger(getClass)

  var instance: Option[Database] = None

  def db: Database = instance match {
    case Some(x) => x
    case _ =>
      logger.error("database not created")
      throw new SQLException("database not created")
  }

  def connect(): Unit = {
    logger.info("connecting to database ... ")
    val config = ConfigFactory.load()
    instance = Some(Database.forConfig(config.getString("databaseConfig")))
    db
    logger.info("connection to database ... done")
  }

  def release() = {
    logger.info("release database resources ...")
    instance match {
      case Some(x) =>
        logger.info("waiting for database shutdown ...")
        x.close()
        instance = None
        logger.info("database shutdown ...  done")
      case _ => ()
    }
    logger.info("release database resources ... done")
  }

  val formatter = new SimpleDateFormat("yyyy-MM-dd")

  val schemaDatabase =
    Subject.models.schema ++
    Status.models.schema ++
    Document.models.schema ++
    User.models.schema ++
    Role.models.schema ++
    UserRole.models.schema ++
    Group.models.schema ++
    Student.models.schema ++
    Essay.models.schema ++
    StudentEssay.models.schema

  val fillQuery = DBIO.seq(
    Subject.models ++= Seq(
      Subject(Some(1), "Математика", Some("Математика")),
      Subject(Some(2), "Техническая механика", Some("Техническая механика")),
      Subject(Some(3), "Физика", Some("Физика")),
      Subject(Some(4), "Автоматизация проектирования", Some("Автоматизация проектирования")),
      Subject(Some(5), "Проектирование баз данных", Some("Проектирование баз данных")),
      Subject(Some(6), "Математика", Some("Математика")),
      Subject(Some(7), "Программирование", Some("Программирование")),
      Subject(Some(8), "Основы алгоритмизации", Some("Основы алгоритмизации")),
      Subject(Some(9), "3D моделирование", Some("3D моделирование")),
      Subject(Some(10), "Распознование образов", Some("Распознование образов"))
    ),
    Status.models ++= Seq(
      Status(Some(1), "НОВЫЙ", None),
      Status(Some(2), "ТРЕБУЕТ ИСПРАВЛЕНИЙ", None),
      Status(Some(3), "ИСПРАВЛЕН", None),
      Status(Some(4), "ВЫПОЛНЕН", None),
      Status(Some(5), "СДАН", None),
      Status(Some(6), "НЕДЕЙСТВИТЕЛЕН", None)
    ),
    Document.models ++= Seq(
      Document(Some(1), "Методическое пособие по высшей математике", "file_1", Some("Методическое пособие по высшей математике"), new Date(formatter.parse("2016-05-22").getTime), 8, 6, None),
      Document(Some(2), "Физика", "file_2", Some("Физика для 2-3 курсов"), new Date(formatter.parse("2016-04-22").getTime), 8, 3, None),
      Document(Some(3), "Автоматизация проектирование", "file_2", Some("Автоматизация проектирование"), new Date(formatter.parse("2016-03-22").getTime), 8, 4, None)
    ),
    User.models ++= Seq(
      User(Some(1), "student1", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(2), "student2", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(3), "student3", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(4), "student4", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(5), "student5", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(6), "student6", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(7), "student7", "studentp", "studentfn", "studentln", Some("studentp")),
      User(Some(8), "teacher1", "teacherp", "Полозков", "teacherln", Some("teacherp")),
      User(Some(9), "teacher2", "teacherp", "Напрасников", "Владимир", Some("Владимирович")),
      User(Some(10), "teacher3", "teacherp", "Вяльцев", "Валентин", Some("Николаевич")),
      User(Some(11), "teacher4", "teacherp", "Ковалева", "Ирина", Some("Львовна")),
      User(Some(12), "teacher5", "teacherp", "Кункевич", "Дмитрий", Some("Петрович")),
      User(Some(13), "admin", "adminp", "adminfn", "adminln", Some("adminp"))
    ),
    Role.models ++= Seq(
      Role(Some(1), "user", Some("user role")),
      Role(Some(2), "student", Some("student role")),
      Role(Some(3), "teacher", Some("teacher role")),
      Role(Some(4), "admin", Some("admin role"))
    ),
    UserRole.models ++= Seq(
      (1, 2),
      (2, 2),
      (3, 2),
      (4, 2),
      (5, 2),
      (6, 2),
      (7, 2),
      (8, 3),
      (9, 3),
      (10, 3),
      (11, 3),
      (12, 3),
      (13, 4)
    ),
    Group.models ++= Seq(
      Group(Some(1), "107521", None),
      Group(Some(2), "107511", None)
    ),

    Essay.models ++= Seq(
      Essay(Some(1), "Реферат на тему 1", "file-id-1", statusId =  1),
      Essay(Some(2), "Реферат на тему 2", "file-id-2", statusId =  2),
      Essay(Some(3), "Реферат на тему 3", "file-id-3", statusId =  3),
      Essay(Some(4), "Реферат на тему 4", "file-id-4", statusId =  4),
      Essay(Some(5), "Реферат на тему 5", "file-id-5", statusId =  5),
      Essay(Some(6), "Реферат на тему 6", "file-id-6", statusId =  6),
      Essay(Some(7), "Реферат на тему 7", "file-id-7", statusId =  1)
    ),
    Student.models ++= Seq(
      Student(Some(1), "Андреев", "Александр", Some("Александрович"), new Date(formatter.parse("1993-01-01").getTime), userId = Some(1), groupId = Some(1), essayId = Some(1), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(2), "Аседова", "Елизавета", Some("Виталиевна"), new Date(formatter.parse("1993-03-01").getTime), userId = Some(2), groupId = Some(1), essayId = Some(2), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(3), "Валинуров", "Денис", Some("Юрьевич"), new Date(formatter.parse("1993-01-03").getTime), userId = Some(3), groupId = Some(1), essayId = Some(3), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(4), "Гаранян", "Ованес", Some("Суренович"), new Date(formatter.parse("1993-07-21").getTime), userId = Some(4), groupId = Some(2), essayId = Some(4), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(5), "Горинова", "Юлия", Some("Юрьевна"), new Date(formatter.parse("1993-05-09").getTime), userId = Some(5), groupId = Some(2), essayId = Some(5), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(6), "Ермохин", "Макар", Some("Андреевич"), new Date(formatter.parse("1993-05-01").getTime), userId = Some(6), groupId = Some(2), essayId = Some(6), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(7), "Иванов", "Павел", Some("Александрович"), new Date(formatter.parse("1993-08-27").getTime), userId = Some(7), groupId = Some(2), essayId = Some(7), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b")
    )
  )


  def fillData() = {
    db.run(DBIO.seq(
      sqlu"SET foreign_key_checks = 0 ",
      fillQuery,
      sqlu"SET foreign_key_checks = 1 "
    ))
  }

  def createSchema() = db.run(schemaDatabase.create)

  def dropSchema() = db.run(schemaDatabase.drop)

}
