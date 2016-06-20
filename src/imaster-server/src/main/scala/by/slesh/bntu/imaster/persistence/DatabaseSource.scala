package by.slesh.bntu.imaster.persistence

import java.sql.{SQLException, Date}
import java.text.SimpleDateFormat

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import slick.ast.{TypedType, ColumnOption}
import slick.driver.H2Driver.api._

/**
  * @author yauheni.putsykovich
  */

object DatabaseSource {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  private val logger = LoggerFactory.getLogger(getClass)

  def db: Database = instance match {
    case Some(x) => x
    case _ =>
      logger.error("database not created")
      throw new SQLException("database not created")
  }

  var instance: Option[Database] = None

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
    Teacher.models.schema ++
    UserEssay.models.schema ++
    TeacherGroup.models.schema

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
      Document(Some(1), Some("Методическое пособие по высшей математике"), Some("file_1"), Some("Методическое пособие по высшей математике"), Some(new Date(formatter.parse("2016-05-22").getTime)), Some(8), Some(6), None),
      Document(Some(2), Some("Физика"), Some("file_2"), Some("Физика для 2-3 курсов"), Some(new Date(formatter.parse("2016-04-22").getTime)), Some(8), Some(3), None),
      Document(Some(3), Some("Автоматизация проектирование"), Some("file_2"), Some("Автоматизация проектирование"), Some(new Date(formatter.parse("2016-03-22").getTime)), Some(8), Some(4), None)
    ),
    User.models ++= Seq(
      User(Some(1), "student1", "studentp"),
      User(Some(2), "student2", "studentp"),
      User(Some(3), "student3", "studentp"),
      User(Some(4), "student4", "studentp"),
      User(Some(5), "student5", "studentp"),
      User(Some(6), "student6", "studentp"),
      User(Some(7), "student7", "studentp"),
      User(Some(8), "teacher1", "teacherp"),
      User(Some(9), "teacher2", "teacherp"),
      User(Some(10), "teacher3", "teacherp"),
      User(Some(11), "teacher4", "teacherp"),
      User(Some(12), "teacher5", "teacherp"),
      User(Some(13), "admin", "adminp")
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
      Essay(Some(1), "Информационный язык как средство представления информации", "file-id-1", statusId =  Some(1)),
      Essay(Some(2), "Основные способы представления информации и команд в компьютере", "file-id-2", statusId =  Some(2)),
      Essay(Some(3), "Современные мультимедийные технологии", "file-id-3", statusId =  Some(3)),
      Essay(Some(4), "Кейс-технологии как основные средства разработки программных систем", "file-id-4", statusId =  Some(4)),
      Essay(Some(5), "Современные технологии и их возможности5", "file-id-5", statusId =  Some(5)),
      Essay(Some(6), "Сканирование и системы, обеспечивающие распознавание символов", "file-id-6", statusId =  Some(6)),
      Essay(Some(7), "Всемирная сеть Интернет: доступы к сети и основные каналы связи", "file-id-7", statusId =  Some(1))
    ),
    Student.models ++= Seq(
      Student(Some(1), "Андреев", "Александр", Some("Александрович"), "alexander@gmail.com",  new Date(formatter.parse("1993-01-01").getTime), groupId = Some(1), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(2), "Аседова", "Елизавета", Some("Виталиевна"), "elisaveta@gmail.com", new Date(formatter.parse("1993-03-01").getTime),  groupId = Some(1), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(3), "Валинуров", "Денис", Some("Юрьевич"), "den@gmail.com", new Date(formatter.parse("1993-01-03").getTime), groupId = Some(1), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(4), "Гаранян", "Ованес", Some("Суренович"), "ovanes@gmail.com", new Date(formatter.parse("1993-07-21").getTime), groupId = Some(2), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(5), "Горинова", "Юлия", Some("Юрьевна"), "julia@gmail.com", new Date(formatter.parse("1993-05-09").getTime), groupId = Some(2), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(6), "Ермохин", "Макар", Some("Андреевич"), "makar@gmail.com", new Date(formatter.parse("1993-05-01").getTime), groupId = Some(2), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b"),
      Student(Some(7), "Иванов", "Павел", Some("Александрович"), "pavel@gmail.com", new Date(formatter.parse("1993-08-27").getTime), groupId = Some(2), personalCardId = "e88bb9109c62fc1c69da5b17679514c9a051158b")
    ),
    UserEssay.models ++= Seq(
      UserEssay(1, 1),
      UserEssay(2, 2),
      UserEssay(3, 3),
      UserEssay(4, 4),
      UserEssay(5, 5),
      UserEssay(6, 6),
      UserEssay(7, 7)
    ),
    Teacher.models ++= Seq(
      Teacher(Some(8), "Ковалева1", "Ирина1", Some("Львовна1"), "elisaveta@gmail.com", new Date(formatter.parse("1993-03-01").getTime)),
      Teacher(Some(9), "Ковалева2", "Ирина2", Some("Львовна2"), "elisaveta@gmail.com", new Date(formatter.parse("1993-03-01").getTime)),
      Teacher(Some(10), "Ковалева3", "Ирина3", Some("Львовна3"), "elisaveta@gmail.com", new Date(formatter.parse("1993-03-01").getTime)),
      Teacher(Some(11), "Ковалева4", "Ирина4", Some("Львовна4"), "elisaveta@gmail.com", new Date(formatter.parse("1993-03-01").getTime)),
      Teacher(Some(12), "Ковалева5", "Ирина5", Some("Львовна5"), "elisaveta@gmail.com", new Date(formatter.parse("1993-03-01").getTime)),
      Teacher(Some(13), "Полозков", "Юрий", Some("Владимирович"), "admin@gmail.com", new Date(formatter.parse("1993-03-01").getTime))
    ),
    TeacherGroup.models ++= Seq(
      TeacherGroup(8, 1),
      TeacherGroup(9, 2)
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
