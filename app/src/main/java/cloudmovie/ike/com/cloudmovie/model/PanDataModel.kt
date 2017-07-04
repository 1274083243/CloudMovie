package cloudmovie.ike.com.cloudmovie.model

/**
 * 云盘model
 */
class PanDataModel constructor(name:String,path:String) {
    var name:String?=null
    var path:String?=null
    init {
       this.name=name
        this.path=path
    }
}