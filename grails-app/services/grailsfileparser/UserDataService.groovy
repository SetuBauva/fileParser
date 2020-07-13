package grailsfileparser

import com.demo.model.UserData
import grails.gorm.transactions.Transactional

/**
* Service to parse text file and upload data
 */
@Transactional
class UserDataService {

    /**
     * Parse the uploaded file and save in DataBase if no errors in file.
     * If errors exist rollback and bring data to initial state and return errors
     * to controller
     *
     * @param file which is to be parsed
     * @return Map of result which contains information of file parsing success or failure
     */
    def parseFile(def file) {

        def data
        def userId
        def amountOfCoins
        def userName
        def userDataSave
        def error = false
        def result = [:]

        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))
        String strLine
        def lineNumber = 0

        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {

            lineNumber++
            data = strLine.split(",")
            userId = data[0]

            if (data.size() == 1) amountOfCoins = 0
            else amountOfCoins = data[1]

            if (data.size() == 2) userName = ""
            else userName = data[2]

            userDataSave = new UserData(userId: userId, amountOfCoins: amountOfCoins, userName: userName)

            if (userDataSave.hasErrors() || userDataSave.save(validate: true, flush: true) == null) {
                log.error("Could not upload file  ${userDataSave.errors}")

                transactionStatus.setRollbackOnly()
                error=true
                break
            }
        }
        result.error = error
        result.lineNumber = lineNumber
        result.userDataSave = userDataSave
        return result
    }
}
