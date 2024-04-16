package apariciomeli.tutorial.kotlinTutorial.repo

import apariciomeli.tutorial.kotlinTutorial.model.ModuleData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface ModuleDataRepository: CrudRepository<ModuleData, Int>, JpaRepository<ModuleData,Int> {
    fun findAllByModuleId(moduleId:Int): List<ModuleData>
}