package com.example.myapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class ClogAppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.clog_app_widget)

    views.setOnClickPendingIntent(R.id.widgetAll, setMyAction(context))
    views.setTextViewText(R.id.widgetTemp, HomeFragment.widgetTemp)
    views.setTextViewText(R.id.widgetTempMm, HomeFragment.widgetTempMm)
    views.setTextViewText(R.id.widgetCity, HomeFragment.widgetCity)
    views.setTextViewText(R.id.widgetWeather, HomeFragment.widgetWeather)

    if (HomeFragment.widgetText != "") {
        views.setTextViewText(R.id.widgetText, HomeFragment.widgetText)
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun setMyAction(context: Context?): PendingIntent {
    val intent = Intent(context, MainActivity::class.java)
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
}
