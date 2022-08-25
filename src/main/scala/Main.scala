import net.mem_memov.binet.*

@main def hello: Unit =

  println(Test.collectBackdatedIds(List(
    Test.Record(1, 100),
    Test.Record(2, 10),
    Test.Record(3, 30),
    Test.Record(4, 400),
    Test.Record(5, 500),
    Test.Record(6, 50),
  )))

object Test:

  case class Record(id: Long, date: Long)

  def collectBackdatedIds(records: List[Record]): List[Long] =

    records.sortBy(_.id).foldLeft((List.empty[Long], Option.empty[Long])) { case ((backdated, maxDate), record) =>

      maxDate match
        case None =>
          (backdated, Some(record.date))
        case curr @ Some(date) =>
          if date > record.date then
            (record.id :: backdated, curr)
          else
            (backdated, Some(record.date))
    }._1.reverse











