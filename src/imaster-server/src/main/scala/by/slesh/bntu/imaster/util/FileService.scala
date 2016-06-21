package by.slesh.bntu.imaster.util

import java.nio.file.Paths

/**
  * @author slesh
  */
object FileService {
  val storagePath = "/home/slesh/trash/imaster-storage"

  {
    /*initialization block*/
    Paths.get(storagePath).toFile.mkdir()
  }

  def create(fileId: String) = {
    val file = Paths.get("/home/slesh/trash/imaster", fileId).toFile
    if(file.exists()) file.delete()
    file.createNewFile()
    file
  }
}
