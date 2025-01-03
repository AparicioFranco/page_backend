package apariciomeli.tutorial.kotlinTutorial.model

import jakarta.persistence.*

@Entity
data class ModuleData (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    val module: Module,
    val title: String,
    @Column(columnDefinition = "varchar(1000)")
    val text: String? = null,
    val video: String? = null,
    val audio: String,
    val link: String? = null,
    var linkText: String? = null,
    val file: String? = null,
    val fileText: String? = null,
){

}