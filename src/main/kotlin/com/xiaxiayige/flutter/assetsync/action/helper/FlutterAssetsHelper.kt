package com.xiaxiayige.flutter.assetsync.action.helper

import com.google.common.collect.ArrayListMultimap
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.xiaxiayige.flutter.assetsync.common.Constants.Companion.ASSETS
import com.xiaxiayige.flutter.assetsync.common.Constants.Companion.LIB
import com.xiaxiayige.flutter.assetsync.common.Constants.Companion.PUBSPEC
import com.xiaxiayige.flutter.assetsync.entity.FileModel
import com.xiaxiayige.flutter.assetsync.utils.YamlFileUtils
import java.io.File

/**
 * @description: 关于flutter相关的帮助类
 * @author : xiaxiayige@163.com
 * @date: 2021/11/27
 * @version: 1.1.0
 */
class FlutterAssetsHelper(private val project: Project?) {

    /**
     * 获取当前项目的目录地址
     */
    private val projectBaseDir = project?.basePath ?: ""

    /**
     * 检查是否是一个Flutter项目
     */
    fun checkIsFlutterProject(): Boolean {
        val yamlFile = File(projectBaseDir, PUBSPEC)
        val libDir = File(projectBaseDir + File.separator + LIB)
        if (yamlFile.exists() && libDir.exists() && libDir.isDirectory) {
            return true
        }
        return false
    }


    /**
     * 检查assets文件夹是否存在
     */
    fun checkAssetsDir(): Boolean {
        val file = File(projectBaseDir + File.separator + ASSETS)
        return file.exists() && file.isDirectory
    }


    /**
     * 获取所有资源文件
     */
    private fun getAssetsAllFiles(arrayListMultimap: ArrayListMultimap<String, String>, files: Array<File>) {
        for (file in files) {
            if (file.isDirectory) {
                if (!file.name.startsWith("1.") &&
                    !file.name.startsWith("2.") &&
                    !file.name.startsWith("3.") &&
                    !file.name.startsWith("4.")
                ) {
                    file.listFiles()?.let { getAssetsAllFiles(arrayListMultimap, it) }
                }
            } else {
                arrayListMultimap.put(
                    file.parentFile.absolutePath.replace("\\", "/").replace(projectBaseDir, ""),
                    file.name
                )
            }
        }
    }

    fun startWork() {

        val assetsFileDir = File((projectBaseDir + File.separator + ASSETS).replace("\\", "/"))

        val arrayOfFiles = assetsFileDir.listFiles()
        arrayOfFiles?.let {
            //获取所有文件
            val arrayListMultimap = ArrayListMultimap.create<String, String>()
           getAssetsAllFiles(arrayListMultimap,it)

            val fileModels = ArrayList<FileModel>()

            filterValidFile(arrayListMultimap, fileModels)

            println("fileList = ${arrayListMultimap.size()}")

            val yamlPath = (projectBaseDir + File.separator + PUBSPEC).replace("\\", "/")

            val result = YamlFileUtils.writYamlFile(fileModels, yamlPath, projectBaseDir)

            if (!result.isNullOrEmpty()) {
                Messages.showMessageDialog("$result :(", "Tips", null)
            }

//            project?.workspaceFile?.refresh(false, true)
            project?.getBaseDir()?.refresh(false, true);
        }

    }

    /**
     * 过滤出有效的文件
     */
    private fun filterValidFile(fileList: ArrayListMultimap<String, String>, fileModels: ArrayList<FileModel>) {
        val regex1 = Regex("^[0-9]") //数字开头
        val matches = regex1.toPattern()
        fileList.forEach continues@{ t, u ->
            //检查是否存在数字开头的文件名
            val isExist = matches.matcher(u).find()
            //过滤.开头的文件或者文件夹名称和数字开头的文件名称
            if (u.startsWith(".") || isExist) {
                return@continues
            } else {
                fileModels.add(FileModel(t, u))
            }
        }
    }
}