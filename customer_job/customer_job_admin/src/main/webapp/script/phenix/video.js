/*
 * 视频播放相关
 */
if(!phenix) phenix = {};
phenix.video = {};

phenix.video.flowplayerUrl = "/pub/lib/videoPlay/flowplayer/flowplayer-3.2.7.swf";
phenix.video.flowplayerCfg = function(){
	return {
       clip:  {
           autoPlay: true,
           autoBuffering: true
       },
       plugins: {
           controls: {
               url: '/pub/lib/videoPlay/flowplayer/flowplayer.controls-3.2.5.swf',
               playlist: false,
               //backgroundColor: '#aedaff',
               tooltips: {
                   buttons: true,
                   play: '播放',
                   pause: '暂停',
                   mute: '静音',
                   unmute: '解除静音',
                   fullscreen: '全屏播放'
               }
           }
       }
	}
};