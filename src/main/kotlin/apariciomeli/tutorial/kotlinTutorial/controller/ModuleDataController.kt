package apariciomeli.tutorial.kotlinTutorial.controller

import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataDTO
import apariciomeli.tutorial.kotlinTutorial.dto.module.ModuleDataTestDTO
import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import apariciomeli.tutorial.kotlinTutorial.repo.ModuleRepository
import apariciomeli.tutorial.kotlinTutorial.service.moduledata.ModuleDataService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin("*")
@RestController
@RequestMapping("/api/module-data")
class ModuleDataController(
    private val moduleDataService: ModuleDataService,
    private val moduleRepository: ModuleRepository
) {
//    @PostMapping("/private/add")
//    fun createModuleData(@RequestBody moduleDataDTO: ModuleDataDTO): ModuleData {
//        return moduleDataService.createModuleData(moduleDataDTO)
//    }

//    @PostMapping("/private/test", consumes = ["multipart/form-data"])
//    fun test(@RequestPart("audio") audio: MultipartFile?, @RequestBody moduleDataTestDTO: ModuleDataTestDTO) {
//        println("Entered Test Controller")
//        if (audio != null) {
//            println("File${audio.name}")
//        }
////        moduleDataService.test(moduleDataTestDTO, file)
//    }

    @PostMapping("/private/test", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun test(@RequestPart("audioFile") audioFile: MultipartFile?,
             @RequestPart("videoFile") videoFile: MultipartFile?,
             @RequestPart("fileFile") fileFile: MultipartFile?,
             @RequestParam("audioText") audioText: String?,
             @RequestParam("video") video: String?,
             @RequestParam("fileText") fileText: String?,
             @RequestParam("file") file: String?,
             @RequestParam("text") text: String?,
             @RequestParam("title") title: String,
             @RequestParam("link") link: String?,
             @RequestParam("linkText") linkText: String?,
             @RequestParam("moduleId") moduleId: Int): ModuleData {

        if (audioFile != null) {
            println("Entered File Controller")
            val moduleDataTestDTO = ModuleDataTestDTO(
                title = title,
                text = text,
                link = link,
                linkText = linkText,
                file = file,
                video = video,
                moduleId = moduleId,
                audioText = audioFile.originalFilename,
            )
            println(moduleDataTestDTO)
            return moduleDataService.uploadFile(audioFile, moduleDataTestDTO)
        }else{
            if (audioText != null) {
                println("Entered Text Controller")
                val moduleData = ModuleData(
                    title = title,
                    text = text,
                    link = link,
                    linkText = linkText,
                    file = file,
                    video = video,
                    module = moduleRepository.findById(moduleId).orElse(null),
                    audio = audioText,
                )
                return moduleDataService.createModuleData(moduleData)
            }else{
                throw Exception("Not an audio selected.")
            }

        }
    }

    @GetMapping("/public/module/{moduleId}")
    fun getModulesDataByModuleId(@PathVariable moduleId: Int): List<ModuleData> {
        return moduleDataService.getModulesDataByModuleId(moduleId).sortedBy { it.id }
    }

    @GetMapping("/private/course/{courseId}")
    fun getModulesDataByCourseId(@PathVariable courseId: Int): List<List<ModuleData>> {
        return moduleDataService.getModulesDataByCourseId(courseId)
    }

    @PutMapping("/private/edit")
    fun changeModuleData(@RequestBody moduleDataDTO: ModuleDataDTO): ModuleData {
        return moduleDataService.changeModuleData(moduleDataDTO)
    }



}