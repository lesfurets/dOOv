package io.doov.sample;

import io.doov.core.FieldModel;
import io.doov.sample.field.SampleFieldId;
import io.doov.sample.model.*;

public class ExampleAccount {

    public class WithJavaBean {

        public String readSomeStuff(SampleModel model) {
            if (model == null)
                return null;
            if (model.getAccount() == null)
                return null;
            if (model.getAccount().getTop3WebSite() == null)
                return null;
            if (model.getAccount().getTop3WebSite().size() < 3)
                return null;
            FavoriteWebsite website = model.getAccount().getTop3WebSite().get(2);
            return website != null ? website.getUrl() : null;
        }

        public void updateSomeStuff(SampleModel model, String url) {
            if (model == null)
                return;
            if (model.getAccount() == null)
                return;
            if (model.getAccount().getTop3WebSite() == null)
                return;
            if (model.getAccount().getTop3WebSite().size() < 3)
                return;
            FavoriteWebsite website = model.getAccount().getTop3WebSite().get(2);
            if (website != null)
                website.setUrl(url);
        }

    }

    public class WithKeyValueModel {

        public FieldModel asFielModel(SampleModel model) {
            return new SampleModelWrapper(model);
        }

        public String readSomeStuff(FieldModel fieldModel) {
            return fieldModel.get(SampleFieldId.FAVORITE_SITE_URL_2);
        }

        public void updateSomeStuff(FieldModel fieldModel, String url) {
            fieldModel.set(SampleFieldId.FAVORITE_SITE_URL_2, url);
        }

    }

}
