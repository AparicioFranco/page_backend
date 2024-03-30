package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
data class ModuleData (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    val module: Module,
    val title: String,
    val text: String,
    val video: String,
    val audio: String,
){

}