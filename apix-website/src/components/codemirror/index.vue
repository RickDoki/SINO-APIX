<template>
  <div>
    <prism-editor v-model='requestExample' class='my-editor height-300' :lineNumbers='true' :highlight='highlighter'
                  readonly></prism-editor>
  </div>
</template>

<script>
import {PrismEditor} from 'vue-prism-editor'
import 'vue-prism-editor/dist/prismeditor.min.css'
import 'prismjs'
import 'prismjs/components/prism-json'
import 'prismjs/components/prism-json.min'
import 'prismjs/components/prism-yaml'
import 'prismjs/components/prism-yaml.min'
import {highlight, languages} from 'prismjs/components/prism-core'
import 'prismjs/components/prism-clike'
import 'prismjs/components/prism-javascript'
import 'prismjs/themes/prism-tomorrow.css' // import syntax highlighting styles
export default {
  props: ['json'],
  components: {
    PrismEditor
  },
  data() {
    return {
      requestExample: ''
    }
  },
  mounted() {
    this.requestExample = this.json
  },
  methods: {
    highlighter(code) {
      return highlight(code, languages.js)
    }
  }
}
</script>

<style scoped>
.my-editor {
  background: #2d2d2d;
  color: #ccc;
  border: 0px;
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}

/* optional */
.prism-editor__textarea:focus {
  outline: none;
}

/* not required: */
.height-300 {
  height: 300px;
}
</style>
