<style scoped>
@import url('../../styles/custom_theme.css');
.img {
  width: 300px;
  height: 200px;
  position: relative;
  vertical-align: top;
  display: inline-block;
  margin-right: 5px;
  margin-top: 5px;
}
</style>
<template>
  <div>
    <Row class="expand-row">
      <Col span="16">
      <span class="expand-key">内容: </span>
      <span class="expand-value">{{ row.content }}</span>
      </Col>
      <Col span="4">
      <span class="expand-key">经度: </span>
      <span class="expand-value">{{ row.lng }}</span>
      </Col>
      <Col span="4">
      <span class="expand-key">纬度: </span>
      <span class="expand-value">{{ row.lat }}</span>
      </Col>
    </Row>
    <img v-img="{group:galleryId}"
         class="img"
         v-for="item in imageCount"
         :src="res_domain+row['img_'+item]"
         v-if="row['img_'+item]!==''">
    <video-player class="vjs-custom-skin"
                  v-if="row.video!==''"
                  :options="playerOptions"
                  :playsinline="true"
                  @ready="playerReadied($event)"></video-player>
  </div>
</template>
<script>
import videojs from 'video.js'
import 'video.js/dist/video-js.css'

export default {
  components: {
  },
  props: {
    row: Object,
    res_domain: String,
    video: String
  },
  data () {
    return {
      imageCount: 9,
      index: null,
      galleryId: '',
      playerOptions: {
        height: '360',
        playbackRates: [0.5, 1, 1.5, 2, 3],
        sources: [{
          type: "video/mp4",
          src: this.res_domain + this.row.video
        }],
        poster: "https://surmon-china.github.io/vue-quill-editor/static/images/surmon-3.jpg",
      }
    }
  },
  methods: {
    gen_gallery () {
      return 'gallery' + Math.floor(Math.random() * 100000)
    },
    playerReadied (player) {
      const track = new videojs.AudioTrack({
        id: 'my-spanish-audio-track',
        kind: 'translation',
        label: 'Spanish',
        language: 'es'
      })
      player.audioTracks().addTrack(track)
      // Get the current player's AudioTrackList object.
      const audioTrackList = player.audioTracks()
      // Listen to the "change" event.
      audioTrackList.addEventListener('change', function () {
        // Log the currently enabled AudioTrack label.
        for (let i = 0; i < audioTrackList.length; i++) {
          const track = audioTrackList[i]
          if (track.enabled) {
            videojs.log(track.label)
            return
          }
        }
      })
    }
  },
  created () {
    let temp = []
    for (var j = 1, len = this.imageCount; j <= len; j++) {
      if (this.row['img' + j] !== '') {
        temp.push(this.res_domain + this.row['img' + j])
      }
    }
    this.images = temp
    this.galleryId = this.gen_gallery()
  }
};
</script>