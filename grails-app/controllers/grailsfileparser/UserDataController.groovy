package grailsfileparser

import com.demo.model.UserData

/**
 * Controller to parse text file and upload data
 */
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

        //Get the file uploaded in view
        def file = request.getFile('file')
        if (file.empty)
            flash.error = "File cannot be empty"

        def result = userDataService.parseFile(file)

        //if uploaded file has error display on same page else save the file and show result on screen
        if(!result.error)
            redirect(action: 'list')
        else
            render(view: 'upload', model: [userDataSave: result.userDataSave, userMessage:""+result.lineNumber])
    }
}