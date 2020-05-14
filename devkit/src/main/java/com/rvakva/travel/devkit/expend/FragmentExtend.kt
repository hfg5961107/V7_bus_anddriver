package com.rvakva.travel.devkit.expend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:05
 */
fun FragmentManager.addFragment(
    targetFragment: Fragment,
    containerId: Int,
    addToBackStack: Boolean = false
) {
    commit {
        findFragmentByTag(targetFragment::class.java.name) ?: let {
            add(containerId, targetFragment, targetFragment::class.java.name)
            setMaxLifecycle(targetFragment, Lifecycle.State.RESUMED)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }
    }
}

fun FragmentManager.switchFragment(
    targetFragment: Fragment,
    currentFragment: Fragment?,
    containerId: Int,
    transition: Int = 0,
    addToBackStack: Boolean = false
) {
    if (currentFragment === targetFragment || targetFragment.isResumed) {
        return
    }
    let { manager ->
        manager.commit {
            if (transition > 0) {
                setTransition(transition)
            }
            (manager.findFragmentByTag(targetFragment::class.java.name)?.let {
                show(it)
                it
            } ?: let {
                add(containerId, targetFragment, targetFragment::class.java.name)
                targetFragment
            }).let {
                setMaxLifecycle(it, Lifecycle.State.RESUMED)
            }

            currentFragment?.let {
                manager.findFragmentByTag(it::class.java.name)?.let {
                    hide(it)
                    setMaxLifecycle(it, Lifecycle.State.STARTED)
                }
            }

            if (addToBackStack) {
                addToBackStack(null)
            }
        }
    }
}