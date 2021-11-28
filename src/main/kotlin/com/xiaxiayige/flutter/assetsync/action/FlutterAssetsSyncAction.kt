package com.xiaxiayige.flutter.assetsync.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.xiaxiayige.flutter.assetsync.action.helper.FlutterAssetsHelper

/**
 * @description: Flutter资源同步Action
 * @author : xiaxiayige@163.com
 * @date: 2021/11/27
 * @version: 1.1.0
 */
class FlutterAssetsSyncAction : AnAction() {

    private lateinit var helper: FlutterAssetsHelper

    override fun actionPerformed(e: AnActionEvent) {

        helper = FlutterAssetsHelper(e.project)

        if (!helper.checkIsFlutterProject()) {
            Messages.showMessageDialog("This is not a Flutter project \n:(", "Tips", null)
            return
        }

        if (!helper.checkAssetsDir()) {
            Messages.showMessageDialog("Please check 'assets' folder is exist \n:(", "Tips", null)
            return
        }

        helper.startWork()

    }
}