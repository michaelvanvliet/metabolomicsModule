modules = {

    jqueryUi {

        dependsOn 'jquery'
        resource url: '/js/jquery-ui-1.8.14.custom.min.js'

    }

    parseConfiguration {

        dependsOn 'jqueryUi, moduleBase'

        resource url: '/js/parseConfiguration.js'
        resource url: '/js/jquery.dataTables.min.js'
        resource url: '/css/parseConfiguration.css'

    }

}