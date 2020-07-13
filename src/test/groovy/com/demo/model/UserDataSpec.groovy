package com.demo.model

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserDataSpec extends Specification implements DomainUnitTest<UserData> {

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses() { [UserData] }

    void 'test userId validations'() {
        when:
        domain.userId = value

        then:
        expected == domain.validate(['userId'])
        domain.errors['userId']?.code == expectedErrorCode

        where:
        value                  | expected | expectedErrorCode
        1111111111             | true     | null
        11111111111            | false    | 'length'
        'A111111111'           | false    | 'matches.invalid'
        ''                     | false    | 'blank'
    }

    def "test userId unique constraint"() {

        when: 'You instantiate a user with userId,amountOfCoins and userName which has been never used before'
        def user = new UserData(userId: '1111111111', amountOfCoins: 2000, userName: 'test')

        then: 'user is valid instance'
        user.validate()

        and: 'we can save it, and we get back a not null GORM Entity'
        user.save()

        and: 'there is one additional user'
        UserData.count() == old(UserData.count()) + 1

        when: 'instanting a different user with the same userId'
        def user1 = new UserData(userId: '1111111111', amountOfCoins: 3000, userName: 'test1')

        then: 'the user instance is not valid'
        !user1.validate(['userId'])

        and: 'unique error code is populated'
        user1.errors['userId']?.code == 'unique'

        and: 'trying to save fails too'
        !user1.save()

        and: 'no user has been added'
        UserData.count() == old(UserData.count())
    }

    void 'test amountOfCoins validation'() {
        when:
        domain.amountOfCoins = value

        then:
        expected == domain.validate(['amountOfCoins'])
        domain.errors['amountOfCoins']?.code == expectedErrorCode

        where:
        value                  | expected | expectedErrorCode
        2000                   | true     | null
        -10                    | false    | 'naturalNumber'
    }

    void 'userName name cannot be blank'() {
        when:
        domain.userName = ''

        then:
        !domain.validate(['userName'])
    }

}
