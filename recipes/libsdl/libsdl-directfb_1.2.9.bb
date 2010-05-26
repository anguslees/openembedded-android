require libsdl.inc

DEPENDS = "alsa-lib directfb"
DEFAULT_PREFERENCE = "-1"

PR = "${INC_PR}.0"

SRC_URI += "\
  file://explicit-extern-C.patch \
  file://acinclude.m4 \
  file://directfb_obsolete_calls.patch \
"

CFLAGS_append  += " -I${STAGING_INCDIR}/directfb -I${STAGING_INCDIR}/directfb-internal"

EXTRA_OECONF = "--disable-static --disable-debug --enable-cdrom --enable-threads --enable-timers --enable-endian \
                --enable-file --enable-oss --enable-alsa --disable-esd --disable-arts \
                --disable-diskaudio --disable-nas --disable-esd-shared --disable-esdtest \
                --disable-mintaudio --disable-nasm --disable-video-x11 --disable-video-dga \
                --enable-video-fbcon --enable-video-directfb --disable-video-ps2gs \
                --disable-video-xbios --disable-video-gem --disable-video-dummy \
                --disable-video-opengl --enable-input-events --enable-pthreads \
                --disable-video-picogui --disable-video-qtopia --enable-dlopen"

SRC_URI[md5sum] = "80919ef556425ff82a8555ff40a579a0"
SRC_URI[sha256sum] = "d5a168968051536641ab5a3ba5fc234383511c77a8bc5ceb8bed619bdd42e5f9"
