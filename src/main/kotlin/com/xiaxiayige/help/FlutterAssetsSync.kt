package com.xiaxiayige.help

import com.google.common.collect.ArrayListMultimap
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.io.File

/***
 * 规范
 *
 */
class FlutterAssetsSync : AnAction() {

    companion object {
        const val ASSETS = "assets"
        const val PUBSPEC = "pubspec.yaml"
        const val LIB = "lib"
    }

    var project: Project? = null
    private var projectBaseDir = ""
    val arrayListMultimap = ArrayListMultimap.create<String, String>()
    override fun actionPerformed(e: AnActionEvent) {
        arrayListMultimap.clear()
        project = e.project
        projectBaseDir = project?.basePath ?: ""
        if (!checkIsFlutterProject()) {
            Messages.showMessageDialog("这不是一个Flutter项目,不可使用该插件 \n:(", "提示", null)
            return
        }

        if (!checkAssetsDir()) {
            Messages.showMessageDialog("对不起,我只能识别assets文件夹哦 \n:(", "提示", null)
            return
        }
        //file.parentFile {C:\Users\xiaxi\AndroidStudioProjects\MyApplication\assets\b=[b.png, bb.png], C:\Users\xiaxi\AndroidStudioProjects\MyApplication\assets\a=[a.png, aa.png], C:\Users\xiaxi\AndroidStudioProjects\MyApplication\assets=[crane_card_dark.png, fortnightly_card.png, fortnightly_card_dark.png]}
        //projectBaseDir = C:/Users/xiaxi/AndroidStudioProjects/MyApplication

        //开始获取Assets文件夹目录下的文件
        val assetsFileDir = File(projectBaseDir + File.separator + ASSETS)

        getFile(assetsFileDir.listFiles())
        val fileModels = ArrayList<FileModel>()
        arrayListMultimap.forEach { t, u ->
            fileModels.add(FileModel(t, u))
        }

        print(fileModels)

        val result = Utils.writYamlFile(fileModels, projectBaseDir + File.separator + PUBSPEC,projectBaseDir)

        if (!result.isNullOrEmpty()) {
            Messages.showMessageDialog("$result :(", "提示", null)
        }

    }


    private fun getFile(files: Array<File>) {
        for (file in files) {
            if (file.isDirectory
            ) {
                if (!file.name.startsWith("1.") &&
                    !file.name.startsWith("2.") &&
                    !file.name.startsWith("3.") &&
                    !file.name.startsWith("4.")
                ) {
                    getFile(file.listFiles())
                }
            } else {
                arrayListMultimap.put(
                    file.parentFile.absolutePath.replace("\\", "/").replace(projectBaseDir, ""),
                    file.name
                )
            }
        }
    }


    override fun update(e: AnActionEvent) {
        super.update(e)
    }

    //检查assets文件夹是否存在
    private fun checkAssetsDir(): Boolean {
        val file = File(projectBaseDir + File.separator + ASSETS)
        return file.exists() && file.isDirectory
    }

    //检查是否是一个Flutter项目
    fun checkIsFlutterProject(): Boolean {
        val yamlFile = File(projectBaseDir, PUBSPEC)
        val libDir = File(projectBaseDir + File.separator + LIB)
        if (yamlFile.exists() && libDir.exists() && libDir.isDirectory) {
            return true
        }
        return false
    }
}