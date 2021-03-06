package org.dbxp.metabolomicsModule.measurements

import grails.converters.JSON
//import wslite.rest.*

class PocController {
	
	def measurementService
	def identityFactoryService

    def index = {
			
		measurementService.findAllMeasurementPlatforms().each { mp ->
			measurementService.findAllMeasurementPlatformVersions(['measurementPlatform': mp]).each { mpv ->
				render "<h1>${mp.name} (Version: ${mpv.versionNumber})</h1>"
				render "<h2>Features</h2>"
				measurementService.findAllMeasurementPlatformVersionFeatures(['measurementPlatformVersion': mpv]).each { mpvf ->
					render " - - - - - - - - - - - - -<br />"					
					render "<h3>Feature <i><font color=\"blue\">${mpvf.feature.label}</font></i></h3>"					
					render "<b>Properties (feature specific)</b><br />"
					mpvf.feature.props.each { fp ->
						render " - <i>${fp.key}</i> :: <font color=\"blue\">${fp.value}</font><br />"
					}					
					
					render "<b>Properties (platform specific) :</b><br />"
					mpvf.props.each { mpvfp ->
						render " - <i>${mpvfp.key}</i> :: <font color=\"blue\">${mpvfp.value}</font><br />"
					}
					render "<br />"

				}
				render "<br /> #################################################################################### <br /><br />"
			}
		}
	}
	
	def platforms = {
		
		render "<b>FETCH MEASUREMENT PLATFORM VERSIONS PER PLATFORM...</b><br /><br />"
		
		/*
		 * Fetch all Measurement Platform versions (per platform) 
		 */
		def mps = measurementService.findAllMeasurementPlatforms()
		
		mps.sort { a,b -> a.name <=> b.name }.each { mp ->
			render "<b>${mp.name}</b><br />has version(s): "
			
			def mp_mpvs = measurementService.findAllMeasurementPlatformVersions(['measurementPlatform':mp])
			
			mp_mpvs.each {
				render " :: V${it.versionNumber} "
			}
			
			render "<br />"
		}
		
		render "<br /><b>OR FETCH ALL IN ONE GO...</b><br /><br />"
		
		/*
		* Fetch all Measurement Platform versions (in one go)
		*/
		def mpvs = measurementService.findAllMeasurementPlatformVersions()
		
		mpvs.sort { a,b -> a.measurementPlatform.name <=> b.measurementPlatform.name }.each { mpv ->
			render "Platform: ${mpv.measurementPlatform.name} - V${mpv.versionNumber}<br />"
		}
		
		
		
		render "Done"
		
	}
	
	def identityExample = {
		
		def outHashMap = [:]
		def labels = ['PA(12:0/13:0)', 'PA(6:0/6:0)', 'PA(12:0/15:0)', 'unknown']
		
		labels.each { label ->
			outHashMap[label] = identityFactoryService.featureFromLabel(['label':label, 'eager': true])
		}
		
		render (outHashMap as JSON)
	}
	
//	def restTest = {
//		def client = new RESTClient("http://www.fresnostatenews.com/feed/")
//		def response = client.get()
//		println response.dump()
//	}	
}
