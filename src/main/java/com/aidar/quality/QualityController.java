package com.aidar.quality;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class QualityController implements UserPreferencesListener {
    private final QualityContext context;
    private final QualitySelector selector;
    private final NetworkSpeedProvider network;
    private final Consumer<String> report;
    private Function<StreamRequest, VideoQualityStrategy> selectionRule;

    public QualityController(QualityContext context, QualitySelector selector,
                             NetworkSpeedProvider network, Consumer<String> report) {
        this.context = Objects.requireNonNull(context);
        this.selector = Objects.requireNonNull(selector);
        this.network = Objects.requireNonNull(network);
        this.report = Objects.requireNonNull(report);
        this.selectionRule = request -> context.getStrategy(); // keep whatever the context starts with
    }

    /** User picked a mode by name: this strategy stays until the user picks another one. */
    public void chooseManually(String mode) {
        VideoQualityStrategy chosen = selector.selectStrategy(mode);
        selectionRule = request -> chosen;
        refresh();
    }

    /** User handed the decision to the Selector: it re-decides on every change. */
    public void chooseAutomatically() {
        selectionRule = request -> selector.selectAutomatically(request, network);
        refresh();
    }

    @Override
    public void onPreferencesChanged(StreamRequest newRequest) {
        context.setRequest(newRequest);
        refresh();
    }

    private void refresh() {
        context.setStrategy(selectionRule.apply(context.getRequest()));
        report.accept(context.getStrategy().getClass().getSimpleName() + " -> " + context.getVideoQuality());
    }
}
