package apariciomeli.tutorial.kotlinTutorial.dto.module

import org.springframework.web.multipart.MultipartFile

class ModuleDataDTO (
    var id: Int?,
    var moduleId: Int,
    var title: String,
    var text: String,
    var video: String,
    var audio: String,
    var link: String,
    var linkText: String,
    var file: String
)

class ModuleDataTestDTO (
    var moduleId: Int,
    var title: String,
    var text: String?,
    var video: String?,
    var audioText: String?,
    var link: String?,
    var linkText: String?,
    var file: String?
)

