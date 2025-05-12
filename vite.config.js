import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig({

  root: 'src/main/frontend',

  plugins: [vue()],

  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src/main/frontend/src'),
    },
  },

  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
    },
  },

  build: {
    outDir: path.resolve(__dirname, 'src/main/resources/static'),
    emptyOutDir: true,
    rollupOptions: {
      input: path.resolve(__dirname, 'src/main/frontend/index.html'),
    },
  },
});
