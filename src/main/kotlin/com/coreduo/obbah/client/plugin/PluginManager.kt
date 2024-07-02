package com.coreduo.obbah.client.plugin

import com.coreduo.obbah.HabboCommunicator
import com.google.gson.Gson
import java.io.File
import java.io.InputStreamReader
import java.net.URLClassLoader
import java.util.jar.JarFile
import kotlin.reflect.full.primaryConstructor

class PluginManager(private val pluginsDir: File, private val communicator: HabboCommunicator) {

    private val plugins = mutableListOf<HabboPlugin>()
    private val gson = Gson()

    fun loadPlugins() {
        if (!pluginsDir.exists() || !pluginsDir.isDirectory) {
            println("Plugins directory does not exist or is not a directory.")
            return
        }

        val jarFiles = pluginsDir.listFiles { _, name -> name.endsWith(".jar") } ?: return
        val urls = jarFiles.map { it.toURI().toURL() }.toTypedArray()
        val classLoader = URLClassLoader(urls, this::class.java.classLoader)

        jarFiles.forEach { jarFile ->
            try {
                val pluginConfig = readPluginConfig(jarFile)
                val pluginClass = classLoader.loadClass(pluginConfig.main).kotlin
                val constructor = pluginClass.primaryConstructor

                if (constructor != null && constructor.parameters.isEmpty()) {
                    val plugin = constructor.call() as HabboPlugin
                    plugin.communicator = communicator
                    plugins.add(plugin)
                    plugin.onEnable()
                    println("Loaded plugin from ${jarFile.name}")
                } else {
                    println("Failed to load plugin from ${jarFile.name}: No-argument constructor not found.")
                }
            } catch (e: Exception) {
                println("Failed to load plugin from ${jarFile.name}")
                e.printStackTrace()
            }
        }
    }

    private fun readPluginConfig(jarFile: File): PluginConfiguration {
        JarFile(jarFile).use { jar ->
            val entry = jar.getJarEntry("plugin.json") ?: throw IllegalArgumentException("plugin.json not found in ${jarFile.name}")
            jar.getInputStream(entry).use { inputStream ->
                val reader = InputStreamReader(inputStream)
                return gson.fromJson(reader, PluginConfiguration::class.java)
            }
        }
    }

    fun getPlugins(): List<HabboPlugin> = plugins

    fun unloadPlugins() {
        plugins.forEach { it.onDisable() }
        plugins.clear()
        println("[PLUGIN MANAGER]: All plugins unloaded")
    }
}