<template>
  <div class="about">
    <v-header/>
    <v-sidebar/>
    <div class="content-box" :class="{ 'content-collapse': collapse }">
      <v-tags></v-tags>
<!--      <div class="login-wrap">-->
        <div class="content">
          <router-view v-slot="{ Component }">
            <transition name="move" mode="out-in">
              <keep-alive :include="tagsList">
                <component :is="Component"/>

              </keep-alive>
            </transition>
          </router-view>
          <!-- <el-backtop target=".content"></el-backtop> -->
        </div>
<!--      </div>-->
    </div>
  </div>
</template>
<script>
import vHeader from "../components/Header";
import vSidebar from "../components/Sidebar";
import vTags from "../components/Tags.vue";

export default {
  components: {
    vHeader,
    vSidebar,
    vTags
  },
  computed: {
    tagsList() {
      return this.$store.state.tagsList.map(item => item.name);
    },
    collapse() {
      return this.$store.state.collapse;
    }
  }
};
</script>
<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/neu1.jpg);
  background-size: 90%;
}
</style>
