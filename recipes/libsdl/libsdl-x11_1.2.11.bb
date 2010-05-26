require libsdl.inc

# extra-keys.patch is missing
DEFAULT_PREFERENCE = "-1" 

PR = "${INC_PR}.0"

SRC_URI = "\
  http://www.libsdl.org/release/SDL-${PV}.tar.gz \
  file://acinclude.m4 \
  file://configure_tweak.patch \
  file://pagesize.patch \
  file://kernel-asm-page.patch \
  file://sdl-cdfix.patch \
  file://fixmfour.patch \
  file://libsdl-1.2.11-pagesize.patch \
"

EXTRA_OECONF = "--disable-static --disable-debug --enable-cdrom --enable-threads --enable-timers --enable-endian \
                --enable-file --enable-oss --enable-alsa --disable-esd --disable-arts \
                --disable-diskaudio --disable-nas --disable-esd-shared --disable-esdtest \
                --disable-mintaudio --disable-nasm --enable-video-x11 --disable-video-dga \
                --enable-video-fbcon --disable-video-directfb --disable-video-ps2gs \
                --disable-video-xbios --disable-video-gem --disable-video-dummy \
                --enable-video-opengl --enable-input-events --enable-pthreads \
                --disable-video-picogui --disable-video-qtopia --enable-dlopen"


SRC_URI[md5sum] = "418b42956b7cd103bfab1b9077ccc149"
SRC_URI[sha256sum] = "6985823287b224b57390b1c1b6cbc54cc9a7d7757fbf9934ed20754b4cd23730"
