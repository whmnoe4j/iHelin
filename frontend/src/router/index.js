import Vue from "vue";
import Router from "vue-router";
import Home from "../pages/home.vue";
import Index from "../pages/index.vue";
import Test from "../pages/test.vue";
import NotFound from "../pages/404.vue";
import FiCustomer from "../pages/fi/customer.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      redirect: 'index',
      component: Home,
      children: [{
        path: 'index',
        component: Index
      }, {
        path: 'fi/customer',
        component: FiCustomer
      }, {
        path: 'test',
        component: Test
      }]
    }, {
      path: '*',
      component: NotFound
    }
  ]
})
