# FlutterAssetSync

Flutter Assets Image Sync, Automatic generated r.dart file ,reference image resource file .(Same as Android R.class)

# How to use

## Step 1

	Create 'assets' folder in your flutter project(this plugin only support identify 'assets' folder )


![](imgs/1.gif)

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
