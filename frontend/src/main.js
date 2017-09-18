// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import ElementUI from "element-ui";
import App from "./App";
import router from "./router";
import Resource from "vue-resource";
import "element-ui/lib/theme-default/index.css";
import "../../src/main/webapp/css/bootstrap.min.css";

Vue.use(ElementUI);
Vue.use(Resource);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
});
