package apariciomeli.tutorial.kotlinTutorial.service.moduledata

import apariciomeli.tutorial.kotlinTutorial.config.S3Config
import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataTestDTO
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleDataMapper
import apariciomeli.tutorial.kotlinTutorial.mapper.ModuleDataTestMapper
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.repo.CourseRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleDataRepository
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.model.PutObjectRequest


@Service
class ModuleDataServiceImpl(
  private val moduleDataRepository: ModuleDataRepository,
  private val moduleDataMapper: ModuleDataMapper,
  private val moduleDataTestMapper: ModuleDataTestMapper,
  private val moduleRepository: ModuleRepository,
  private val courseRepository: CourseRepository,
  private val s3Client: S3Config,
) : ModuleDataService {

  private final val API_URL = "https://audio-bucket.fra1.digitaloceanspaces.com/";

  fun uploadFile(file: MultipartFile) {
    val client = s3Client.s3Client()
    val fileName = file.originalFilename

    client.putObject(
      PutObjectRequest.builder()
        .bucket("audio-bucket")
        .key(fileName)
        .acl("public-read")
        .build(),
      RequestBody.fromInputStream(file.inputStream, file.size)
    );

  }

  override fun test(moduleDataTestDTO: ModuleDataTestDTO, file: MultipartFile) {
    println("Entered Test Service")
    println("File $file")
    println("Name ${file.name}")
//    uploadFile(moduleDataTestDTO);
  }


  override fun createModuleData(moduleData: ModuleData): ModuleData {
    return moduleDataRepository.save(moduleData)
  }

  override fun uploadFile(file: MultipartFile, moduleDataTestDTO: ModuleDataTestDTO): ModuleData {
    moduleDataTestDTO.audioText = file.originalFilename
    val moduleData = moduleDataTestMapper.toEntity(moduleDataTestDTO)
    uploadFile(file)
    return moduleDataRepository.save(moduleData)
  }



  override fun getModulesDataByModuleId(moduleId: Int): List<ModuleData> {
    return moduleDataRepository.findAllByModuleId(moduleId)
  }

  override fun getModulesDataContentByModuleDataId(moduleDataId: Int): ModuleData {
    TODO("Not yet implemented")
  }

  override fun getModulesDataByCourseId(courseId: Int): List<List<ModuleData>> {
    val courseOptional = courseRepository.findById(courseId)
    val modulesDataReturn = mutableListOf<List<ModuleData>>()
    if (courseOptional.isPresent) {
      return modulesDataReturn
      //            val modules = moduleRepository.findAllByCourse(courseOptional.get()).sortedBy {
      // it.id }
      //            for (module in modules) {
      //                val moduleDataTemp = moduleDataRepository.findAllByModuleId(module.id)
      //                modulesDataReturn.add(moduleDataTemp)
      //            }
    }
    return modulesDataReturn
  }

  override fun changeModuleData(moduleDataDTO: ModuleDataDTO): ModuleData {
    val moduleData = moduleDataMapper.toEntity(moduleDataDTO)
    return moduleDataRepository.save(moduleData)
  }
}
