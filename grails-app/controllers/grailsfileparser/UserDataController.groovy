package grailsfileparser

import com.demo.model.UserData

class UserDataController {

    def userDataService

    def index() {
        redirect(action: "upload", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userDataInstanceList: UserData.list(params), userDataInstanceTotal: UserData.count()]
    }

    def upload() {
    }

    def doUpload() {

        def file = request.getFile('file')
        if (file.empty)
            flash.message = "File cannot be empty"

        def result = userDataService.parseFile(file)
        if(!result.error)
            redirect(action: 'list')
        else
            render(view: 'upload', model: [userDataSave: result.userDataSave, userMessage:""+result.lineNumber])
    }
}