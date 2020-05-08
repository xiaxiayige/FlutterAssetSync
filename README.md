# FlutterAssetSync

Flutter Assets Image Sync, Automatic generated r.dart file ,reference image resource file .(Same as Android R.class)


# About FlutterAssetSync

	1.Support folder level import
	2.Automatic generated r.dart File 
	3.Automatic generated pubspec.yaml.temp ，current version need you coyp data to source pubspec.yaml file



# How to use

## Step 1

	Create 'assets' folder in your flutter project(this plugin only support identify 'assets' folder )


![](1.gif)

## Setp 2

	Copy your image file to assets foloder (Support Multi-level directory)

![](imgs/2.gif)

## Setp 3

	Check the 'assets' tag of the resource file,Cannot be commented（"#assets"）

- right eg:<font color='green'>    assets:</font>
 
- error eg:<font color='red'>#assets:</font>
	

## Setp 4

	Execute plugin

	
![](imgs/3.gif)


	current version create temp file（pubspec.yaml.temp），copy  pubspec.yaml.temp  all data to pubspec.yaml


## Setp 5

Reference Image Resources

	 Image.asset(R.images_crane_card),
	 Image.asset(R.rally_card)




# Offline Install

### 1.download 

 [https://github.com/xiaxiayige/FlutterAssetSync/releases/download/v1.0.1/fluter_assets_sync-1.0.1-SNAPSHOT.zip](https://github.com/xiaxiayige/FlutterAssetSync/releases/download/v1.0.1/fluter_assets_sync-1.0.1-SNAPSHOT.zip)

### 2.install

	Open your Android Studio ,File -> Settings -> plugins -> (Click Settings Icon) -> 
	Install Plugin from Disk.. -> select this plugin Zip File -> restart idea 


# Change Log

	v1.0.1

	1.Support folder level import
	2.Automatic generated r.dart File 
	3.Automatic generated pubspec.yaml.temp ，need you coyp data to source pubspec.yaml file



