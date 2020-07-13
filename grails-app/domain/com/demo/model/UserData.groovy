package com.demo.model

class UserData {
        String userId
        int amountOfCoins
        String userName

        static constraints = {
                userId  blank: false, unique: true, matches: "^[0-9]*\$", validator: { val1 ->
                        if (val1.length() != 10) {
                                return [('lengthEqualToTen')]

                        }
                }
                amountOfCoins blank: false,  validator: {val ->
                        if (val < 1) {
                                return[('naturalNumber')]

                        }
                }
                userName blank: false
        }
}
