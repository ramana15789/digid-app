package singularity.walkineasy.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;

import singularity.walkineasy.customviews.FormWidget;
import singularity.walkineasy.customviews.MultiChoiceWidget;
import singularity.walkineasy.customviews.SimpleDateWidget;
import singularity.walkineasy.customviews.SimpleRadioWidget;
import singularity.walkineasy.customviews.SimpleTextWidget;
import singularity.walkineasy.http.models.FormDetails;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class LayoutFactory {

    public static final String TAG = "LayoutFactory";

    public static FormWidget getView(@NonNull FormDetails detail, final Context context, final FragmentManager manager) {
        if (detail.formDetailType.equals(FormDetails.FormDetailType.TEXT)) {
            Logger.i(TAG, "Parsing a text field");
            SimpleTextWidget textWidget = new SimpleTextWidget(context);
            textWidget.bindModel(detail);
            return textWidget;
        } else if (detail.formDetailType.equals(FormDetails.FormDetailType.PHONE)) {
            Logger.i(TAG, "Parsing a phone field");
            SimpleTextWidget textWidget = new SimpleTextWidget(context);
            textWidget.bindModel(detail);
            textWidget.setKeyboardToPhoneNumber();
            return textWidget;
        } else if (detail.formDetailType.equals(FormDetails.FormDetailType.EMAIL)) {
            Logger.i(TAG, "Parsing an email field");
            SimpleTextWidget textWidget = new SimpleTextWidget(context);
            textWidget.bindModel(detail);
            textWidget.setKeyboardToEmail();
            return textWidget;
        } else if (detail.formDetailType.equals(FormDetails.FormDetailType.RADIO)) {
            Logger.i(TAG, "Parsing a radio field");
            SimpleRadioWidget radioWidget = new SimpleRadioWidget(context);
            radioWidget.bindModel(detail);
            return radioWidget;
        } else if (detail.formDetailType.equals(FormDetails.FormDetailType.CHECKBOX)) {
            Logger.i(TAG, "Parsing a checkbox field");
            MultiChoiceWidget msWidget = new MultiChoiceWidget(context);
            msWidget.bindModel(detail);
            return msWidget;
        } else if (detail.formDetailType.equals(FormDetails.FormDetailType.TEXTAREA)) {
            Logger.i(TAG, "Parsing a textarea field");
            SimpleTextWidget textWidget = new SimpleTextWidget(context);
            textWidget.bindModel(detail);
            textWidget.setLargeField();

            return textWidget;
        } else if (detail.formDetailType.equals(FormDetails.FormDetailType.DATE)) {
            Logger.i(TAG, "Parsing a date field");
            SimpleDateWidget dateWidget = new SimpleDateWidget(context);
            dateWidget.bindModel(detail);
            dateWidget.setFragmentManager(manager);
            return dateWidget;
        }
        return null;
    }
}
