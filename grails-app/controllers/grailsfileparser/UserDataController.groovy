package grailsfileparser

import com.demo.model.UserData
import grails.gorm.transactions.Transactional

class UserDataController {
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userDataInstanceList: UserData.list(params), userDataInstanceTotal: UserData.count()]
    }

    def upload() {
    }

    @Transactional
    def doUpload() {

       // def theInfoName = "/Users/bhaktithakkarbauva/Documents/userDataGrails.txt"
        def file = request.getFile('file')
        def data
        def userId
        def amountOfCoins
        def userName
        def userDataSave
        def error

        /*file.getInputStream().eachLine  { line ->
          println line
            data = line.split(",")
            userId=data[0]
            amountOfCoins=data[1]

            if (data.size()<3) userName=""
            else userName=data[2]
            userDataSave = new UserData(userId:userId , amountOfCoins:amountOfCoins ,
                  userName:userName)



                if (userDataSave.hasErrors() || userDataSave.save(validate: true, flush: true) == null) {
                    log.error("Could not import domainObject  ${userDataSave.errors}")
                    *//*try {

                        throw new RuntimeException("Exception")
                    }
                    catch (RuntimeException e) {*//*
                        println "before rollback"
                        transactionStatus.setRollbackOnly()

                        //render(view: 'list', model: [userDataSave: userDataSave])

                        true
                    //}
                }

        }
        //render(view: 'list', model: [userDataSave: userDataSave])
       // redirect(action: 'list')*/
        if (file.empty)
            flash.message = "File cannot be empty"

        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String strLine;
        def lineNumber = 0
//Read File Line By Line
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            lineNumber++
            System.out.println("Line--->"+lineNumber+"--->" + strLine);
            data = strLine.split(",")
            userId = data[0]
            amountOfCoins = data[1]

            if (data.size() < 3) userName = ""
            else userName = data[2]
            userDataSave = new UserData(userId: userId, amountOfCoins: amountOfCoins,
                    userName: userName)

            if (userDataSave.hasErrors() || userDataSave.save(validate: true, flush: true) == null) {
                log.error("Could not import domainObject  ${userDataSave.errors}")
                /*try {

            throw new RuntimeException("Exception")
        }
        catch (RuntimeException e) {*/
                println "before rollback"
                transactionStatus.setRollbackOnly()

               // render(view: 'upload', model: [userDataSave: userDataSave])
                render(view: 'upload', model: [userDataSave: userDataSave, userMessage:""+lineNumber])
                //flash.error = "Error in file at line "+lineNumber
                error=true
                break
                //}
            }
        }
        if(!error)
         redirect(action: 'list')
    }
}