# 目的是什么

- [x] 大概了解逻辑
  - [x] 获取所有图片数据
  - [x] 选中图片

- [x] 如何加载图片的
  - [x] 获取加载图片

- [x] 回调如何

- [x] 截图功能回调回的地址怎么生成的

- [x] 截图功能指定输出地址







# PictureSelector 的使用







```xml
<!--默认样式-->
<style name="picture.default.style" parent="Base.Theme.NoActionBar">
    <!-- Customize your theme here. -->
    <!--标题栏背景色-->
    <item name="colorPrimary">@color/picture_color_grey</item>
    <!--状态栏背景色-->
    <item name="colorPrimaryDark">@color/picture_color_grey</item>
    <!--是否改变图片列表界面状态栏字体颜色为黑色-->
    <item name="picture.statusFontColor">false</item>
    <!--返回键图标-->
    <item name="picture.leftBack.icon">@drawable/picture_icon_back</item>
    <!--标题下拉箭头-->
    <item name="picture.arrow_down.icon">@drawable/picture_icon_arrow_down</item>
    <!--标题上拉箭头-->
    <item name="picture.arrow_up.icon">@drawable/picture_icon_arrow_up</item>
    <!--标题文字颜色-->
    <item name="picture.title.textColor">@color/picture_color_white</item>
    <!--标题栏右边文字-->
    <item name="picture.right.textColor">@color/picture_color_white</item>
    <!--图片列表勾选样式-->
    <item name="picture.checked.style">@drawable/picture_checkbox_selector</item>
    <!--开启图片列表勾选数字模式-->
    <item name="picture.style.checkNumMode">false</item>
    <!--选择图片样式0/9-->
    <item name="picture.style.numComplete">false</item>
    <!--图片列表底部背景色-->
    <item name="picture.bottom.bg">@color/picture_color_grey</item>
    <!--图片列表预览文字颜色-->
    <item name="picture.preview.textColor">@color/picture_list_text_color</item>
    <!--图片列表已完成文字颜色-->
    <item name="picture.complete.textColor">@color/picture_list_text_color</item>
    <!--图片已选数量圆点背景色-->
    <item name="picture.num.style">@drawable/picture_num_oval</item>
    <!--预览界面标题文字颜色-->
    <item name="picture.ac_preview.title.textColor">@color/picture_color_white</item>
    <!--预览界面已完成文字颜色-->
    <item name="picture.ac_preview.complete.textColor">@color/picture_list_text_color</item>
    <!--预览界面标题栏背景色-->
    <item name="picture.ac_preview.title.bg">@color/picture_color_grey</item>
    <!--预览界面底部背景色-->
    <item name="picture.ac_preview.bottom.bg">@color/picture_color_grey</item>
    <!--预览界面返回箭头-->
    <item name="picture.preview.leftBack.icon">@drawable/picture_icon_back</item>
    <!--裁剪页面标题背景色-->
    <item name="picture.crop.toolbar.bg">@color/picture_color_grey</item>
    <!--裁剪页面状态栏颜色-->
    <item name="picture.crop.status.color">@color/picture_color_grey</item>
    <!--裁剪页面标题文字颜色-->
    <item name="picture.crop.title.color">@color/picture_color_white</item>
    <!--相册文件夹列表选中图标-->
    <item name="picture.folder_checked_dot">@drawable/picture_orange_oval</item>
    <!--原图勾选样式-->
    <item name="picture.original.check.style">@drawable/picture_original_wechat_checkbox</item>
    <!--原图字体颜色-->
    <item name="picture.original.text.color">@color/picture_color_white</item>
    <!--相册目录文件夹字体颜色-->
    <item name="picture.folder.textColor">@color/picture_color_4d</item>
    <!--相册目录文件夹字体大小-->
    <item name="picture.folder.textSize">16sp</item>
    <!--相册背景底色-->
    <item name="picture.container.backgroundColor">@color/picture_color_white</item>
    <!--标题栏高度-->
    <item name="picture.titleBar.height">48dp</item>
</style>
```



```java
private int chooseMode = PictureMimeType.ofAll(); // 类型


PictureSelector
  .create(PhotoFragment.this)
  .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
  .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
  .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
  .isWeChatStyle(isWeChatStyle)// 是否开启微信图片选择风格
  .isUseCustomCamera(cb_custom_camera.isChecked())// 是否使用自定义相机
  .setLanguage(language)// 设置语言，默认中文
  .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
  .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
  .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
  .isWithVideoImage(true)// 图片和视频是否可以同选
  .maxSelectNum(maxSelectNum)// 最大图片选择数量
  //.minSelectNum(1)// 最小选择数量
  //.minVideoSelectNum(1)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
  .maxVideoSelectNum(1) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
  .imageSpanCount(4)// 每行显示个数
  .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
  //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对.isCompress(false); && .isEnableCrop(false);有效,默认处理
  .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
  .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
  .isOriginalImageControl(cb_original.isChecked())// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
  //.cameraFileName("test.png")    // 重命名拍照文件名、注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
  //.renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
  //.renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
  .selectionMode(cb_choose_mode.isChecked() ?
          PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
  .isSingleDirectReturn(cb_single_back.isChecked())// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
  .isPreviewImage(cb_preview_img.isChecked())// 是否可预览图片
  .isPreviewVideo(cb_preview_video.isChecked())// 是否可预览视频
  //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
  .isEnablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
  .isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
  //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
  .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
  //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
  .isEnableCrop(cb_crop.isChecked())// 是否裁剪
  .isCompress(cb_compress.isChecked())// 是否压缩
  .compressQuality(80)// 图片压缩后输出质量 0~ 100
  .synOrAsy(true)//同步false或异步true 压缩 默认同步
  //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
  //.compressSavePath(getPath())//压缩图片保存地址
  //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
  //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
  .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
  .hideBottomControls(!cb_hide.isChecked())// 是否显示uCrop工具栏，默认不显示
  .isGif(cb_isGif.isChecked())// 是否显示gif图片
  .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
  .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
  //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
  //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
  //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
  .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
  .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
  .isOpenClickSound(cb_voice.isChecked())// 是否开启点击声音
  .selectionData(mAdapter.getData())// 是否传入已选图片
  //.isDragFrame(false)// 是否可拖动裁剪框(固定)
  //.videoMaxSecond(15)
  //.videoMinSecond(10)
  .isPreviewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
  //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
  .cutOutQuality(90)// 裁剪输出质量 默认100
  .minimumCompressSize(100)// 小于100kb的图片不压缩
  //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
  //.rotateEnabled(true) // 裁剪是否可旋转图片
  //.scaleEnabled(true)// 裁剪是否可放大缩小图片
  //.videoQuality()// 视频录制质量 0 or 1
  //.recordVideoSecond()//录制视频秒数 默认60s
  //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径  注：已废弃
  //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
  .forResult(new MyResultCallback(mAdapter));
```





# PictureCustomCameraActivity

## onCreate

* 验证存储权限（没有则申请权限）
* 



# 什么时候获取的图片

* onCreate

* readLocalMedia

  * ```
    LocalMediaPageLoader.getInstance(getContext()).loadAllMedia(
      (OnQueryDataResultListener<LocalMediaFolder>) (data, currentPage, isHasMore) -> {
                if (!isFinishing()) {
                    this.isHasMore = true;
                    initPageModel(data);
                    synchronousCover();
                }
            });
    ```



# PictureBaseActivity.java

```
getResourceId()
initWidgets()
initPictureSelectorStyle()
```



# PictureSelectorActivity.java

```
R.layout.picture_selector;
```

<img src="https://img-blog.csdnimg.cn/20210127143241947.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述" style="zoom: 50%;" />



## 点击取消的时候

```
if (id == R.id.pictureLeftBack // 左箭头
        || id == R.id.picture_right) { // 右边取消按钮
    // 要不folderWindow小时，要不回退
    if (folderWindow != null && folderWindow.isShowing()) {
        folderWindow.dismiss();
    } else {
        onBackPressed();
    }
    return;
}
```



## 点击标题的时候

```
if (id == R.id.picture_title // 标题（相机交卷）
        || id == R.id.ivArrow // 向下指的箭头
        || id == R.id.viewClickMask // 专门用来点击的区域
) {
    if (folderWindow.isShowing()) {
        folderWindow.dismiss();
    } else {
        if (!folderWindow.isEmpty()) {
            folderWindow.showAsDropDown(mTitleBar);
            if (!config.isSingleDirectReturn) {
                List<LocalMedia> selectedImages = mAdapter.getSelectedData(); // 获取数据
                folderWindow.updateFolderCheckStatus(selectedImages);// 更新选中状态
            }
        }
    }
    return;
}
```



## 底部预览

```
if (id == R.id.picture_id_preview) { // 底部预览
    onPreview();
    return;
}
```



## 点击 底部 请选择

```
if (id == R.id.picture_tv_ok // 底部-请选择
        || id == R.id.tv_media_num //选择的个数
) {
    onComplete();
    return;
}
```











## 点击整个标题的时候

```
if (id == R.id.titleBar) {// 整个TitleBar
    if (config.isAutomaticTitleRecyclerTop) {
        int intervalTime = 500;
        if (SystemClock.uptimeMillis() - intervalClickTime < intervalTime) {
            if (mAdapter.getItemCount() > 0) {
                mRecyclerView.scrollToPosition(0); // 滚动到最顶部
            }
        } else {
            intervalClickTime = SystemClock.uptimeMillis();
        }
    }
}
```



## 加载数据-loadAllMediaData

```
/**
 * get LocalMedia s
 */
protected void readLocalMedia() {
    showPleaseDialog();
    if (config.isPageStrategy) {
        LocalMediaPageLoader.getInstance(getContext()).loadAllMedia(
                (OnQueryDataResultListener<LocalMediaFolder>) (data, currentPage, isHasMore) -> {
                    //List<LocalMediaFolder> data, int currentPage, boolean isHasMore
                    if (!isFinishing()) { // Activity被销毁
                        this.isHasMore = true;
                        initPageModel(data);
                        synchronousCover();
                    }
                });
    } else {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMediaFolder>>() {
            @Override
            public List<LocalMediaFolder> doInBackground() {
                return new LocalMediaLoader(getContext()).loadAllMedia();
            }
            @Override
            public void onSuccess(List<LocalMediaFolder> folders) {
                initStandardModel(folders);
            }
        });
    }
}
```





## bothMimeTypeWith()

```
需要裁剪 if(config.enableCrop)
if (config.selectionMode == PictureConfig.SINGLE && isHasImage) {
    config.originalPath = image.getPath();
    UCropManager.ofCrop(this, config.originalPath, image.getMimeType());
} else {
    ArrayList<CutInfo> cuts = new ArrayList<>();
    int count = images.size();
    int imageNum = 0;
    for (int i = 0; i < count; i++) {
        LocalMedia media = images.get(i);
        if (media == null
                || TextUtils.isEmpty(media.getPath())) {
            continue;
        }
        if (PictureMimeType.isHasImage(media.getMimeType())) {
            imageNum++;
        }
        CutInfo cutInfo = new CutInfo();
        cutInfo.setId(media.getId());
        cutInfo.setPath(media.getPath());
        cutInfo.setImageWidth(media.getWidth());
        cutInfo.setImageHeight(media.getHeight());
        cutInfo.setMimeType(media.getMimeType());
        cutInfo.setDuration(media.getDuration());
        cutInfo.setRealPath(media.getRealPath());
        cuts.add(cutInfo);
    }
    if (imageNum <= 0) {
        onResult(images);
    } else {
        UCropManager.ofCrop(this, cuts);
    }
}




```





# PictureSelectionConfig.java









# Android使用MediaStore获取手机上的文件

## 简介

`MediaStore`是`android`系统提供的一个多媒体数据库，专门用于存放多媒体信息的，通过`ContentResolver`即可对数据库进行操作。

​	MediaStore.Files: 共享的文件,包括多媒体和非多媒体信息

​	MediaStore.Audio: 存放音频信息

​	MediaStore.Image: 存放图片信息

​	MediaStore.Vedio: 存放视频信息

每个内部类中都又包含了`Media`,`Thumbnails`和相应的`MediaColumns`，分别提供了媒体信息，缩略信息和 操作字段

## 使用

都是通过 `ContentResolver` 和 `Cursor` 来操作的。

## MediaStore.Files

### 查询所有类型文件

```
// 获取所有文件
public static List<FileEntity> getFilesByType(Context context) {
    List<FileEntity> files = new ArrayList<>();
    // 扫描files文件库
    Cursor c = null;
    try {
        ContentResolver mContentResolver = context.getContentResolver();
        c = mContentResolver.query(MediaStore.Files.getContentUri("external"), null, null, null, null);
        int columnIndexOrThrow_ID = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
        int columnIndexOrThrow_MIME_TYPE = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);
        int columnIndexOrThrow_DATA = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        int columnIndexOrThrow_SIZE = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
        int columnIndexOrThrow_DATE_MODIFIED = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED);
        int tempId = 0;
        while (c.moveToNext()) {
            String path = c.getString(columnIndexOrThrow_DATA);
            String minType = c.getString(columnIndexOrThrow_MIME_TYPE);
            int position_do = path.lastIndexOf(".");
            if (position_do == -1) {
                continue;
            }
            int position_x = path.lastIndexOf(File.separator);
            if (position_x == -1) {
                continue;
            }
            String displayName = path.substring(position_x + 1, path.length());
            long size = c.getLong(columnIndexOrThrow_SIZE);
            long modified_date = c.getLong(columnIndexOrThrow_DATE_MODIFIED);
            File file = new File(path);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));
            FileEntity info = new FileEntity();
            info.setName(displayName);
            info.setPath(path);
            info.setSize(ShowLongFileSize(size));
            info.setId((tempId++) + "");
            info.setTime(time);
            files.add(info);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (c != null) {
            c.close();
        }
    }
    return files;
}
```





### 指定获取的文件字段

```
String[] columns = new String[]{
	MeidaStore.Files.FileColumns._ID,
	MeidaStore.Files.FileColumns.MIME_TYPE,
	MeidaStore.Files.FileColumns.SIZE,
	MeidaStore.Files.FileColumns.DATE_MODIFIED,
	MeidaStore.Files.FileColumns.DATA
};
c = mContentResolver.query(MediaStore.Files.getContent("external"), columns, null, null, null);
```



### 根据文件夹的名称查询

```
//查找文件夹ScreenRecord下的文件
c = mContentResolver.query(MediaStore.Files.getContentUri("external")
	, null
	, MediaStore.Video.Media.BUCKET_DISPLAY_NAME+"=?"
	, "ScreenRecord"
	, null);
```



### 查询指定类型的文件

```
String select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.doc'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.docx'" + ")";
c = mContentResolver.query(MediaStore.Files.getContentUri("external")
	, null
	, select 
	, null
	, null);
```



### 指定排序类型，如根据id倒序查询

```
c = mContentResolver.query(MediaStore.Files.getContentUri("external")
	, null
	, null
	, null
	, MediaStore.Files.FileColumns._ID+"DESC");
```



## MediaStore.Audio

### 查询音频文件

```
c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
	, null
	, null
	, null
	, null);
```



## MediaSotre.Image

### 查询视频文件

```
//获取视频文件
public static List<FileEntity> getFilesByVideo(Context context) {
    List<FileEntity> files = new ArrayList<>();
    // 扫描files文件库
    Cursor c = null;
    try {
        ContentResolver mContentResolver = context.getContentResolver();
        c = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                , null, null, null, null);
        int columnIndexOrThrow_ID = c.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        int columnIndexOrThrow_MIME_TYPE = c.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE);
        int columnIndexOrThrow_DATA = c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        int columnIndexOrThrow_SIZE = c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
        // 更改时间
        int columnIndexOrThrow_DATE_MODIFIED = c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
        int tempId = 0;
        while (c.moveToNext()) {
            String path = c.getString(columnIndexOrThrow_DATA);
            String minType = c.getString(columnIndexOrThrow_MIME_TYPE);
            int position_do = path.lastIndexOf(".");
            if (position_do == -1) {
                continue;
            }
            int position_x = path.lastIndexOf(File.separator);
            if (position_x == -1) {
                continue;
            }
            String displayName = path.substring(position_x + 1, path.length());
            long size = c.getLong(columnIndexOrThrow_SIZE);
            long modified_date = c.getLong(columnIndexOrThrow_DATE_MODIFIED);
            File file = new File(path);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));
            FileEntity info = new FileEntity();
            info.setName(displayName);
            info.setPath(path);
            info.setSize(ShowLongFileSzie(size));
            info.setId((tempId++) + "");
            info.setTime(time);
            files.add(info);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (c != null) c.close();
    }
    return files;
}
```

## query()参数解析

```
public final Cursor query(
	Uri uri,                // 数据资源路径
	String[] projection,    // 查询的列
	String selection,       // 查询的条件
	String[] selectionArgs, // 条件填充值
	String sortOrder        // 排序依据
){}
```



## 数据库字段



```
INTERNAL_CONTENT_URI; //内部库URI
EXTERNAL_CONTENT_URI;//外部库URI
CONTENT_TYPE;//内容提供者类型
DEFAULT_SORT_ORDER;//排序方式
```

MediaStore.Files没有EXTERNAL_CONTENT_URI,所以只能用getContentUri()自定获取，得出的URI其实是

```
Uri.parse("content://media/external/files")
```



URI的三种写法

```
Uri uri1 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
Uri uri2 = MediaStore.Images.Media.getContentUri("external");
Uri uri3 = Uri.parse("content://media/external/images/media");
```



# setCompoundDrawables与setCompoundDrawablesWithIntrinsicBounds的区别



## setCompoundDrawablesWithIntrinsicBounds([Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) left, [Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) top, [Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) right, [Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) bottom)

图标的宽高将会设置为固有宽高

```
button = (RadioButton) group.getChildAt(i);
Resources res = TabTest.this.getResources();
Drawable myImage = res.getDrawable(R.drawable.home);
button.button.setCompoundDrawablesWithIntrinsicBounds(null, myImage, null, null);
```



## setCompoundDrawables([Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) left, [Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) top, [Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) right, [Drawable](https://blog.csdn.net/wulianghuan/article/details/24421179) bottom)

这个方法要先给Drawable设置setBounds(x,y,width,height);

```
Resources res = TabTest.this.getResources();
Drawable myImage = res.getDrawable(R.drawable.home);
myImage.setBounds(1,1,100,100);
button.setCompoundDrawables(null, myImage, null, null);
```

