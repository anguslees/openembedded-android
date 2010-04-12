DESCRIPTION = "Open Source multimedia player."
SECTION = "multimedia"
PRIORITY = "optional"
HOMEPAGE = "http://www.mplayerhq.hu/"
DEPENDS = "live555 libdvdread libtheora virtual/libsdl ffmpeg xsp zlib libpng jpeg liba52 freetype fontconfig alsa-lib lzo ncurses lame libxv virtual/libx11 virtual/kernel \
	   ${@base_conditional('ENTERPRISE_DISTRO', '1', '', 'libmad liba52 lame', d)}"

RDEPENDS = "mplayer-common"
LICENSE = "GPL"
SRC_URI = "svn://svn.mplayerhq.hu/mplayer;module=trunk \
	   file://makefile-nostrip-svn.patch;patch=1 \
	   file://mplayer-arm-pld.patch;patch=1 \
	   file://mplayer-lavc-arm.patch;patch=1 \
       file://fix-exp.diff;patch=1;maxrev=30291 \
	   file://fix-addrinfo.patch;patch=1;maxrev=30302 \
       file://fix-avconfig.diff;patch=1;maxrev=30376 \
"

SRCREV = "30165"
SRC_URI_append_armv7a = " \
		file://omapfb.patch;patch=1 \
	   file://vo_omapfb.c \
	   file://yuv.S \
	  "

# This is required for the collie machine only as all stacks in that
# machine seem to be set to executable by the toolchain. If someone
# discovers this is more general than please make this more general
# ie. for all armv4 machines.
SRC_URI_append_collie = "file://disable-executable-stack-test.patch;patch=1"

PACKAGE_ARCH_collie = "collie"
PACKAGE_ARCH_c7x0 = "c7x0"
PACKAGE_ARCH_hx4700 = "hx4700"

ARM_INSTRUCTION_SET = "ARM"

RCONFLICTS_${PN} = "mplayer-atty"
RREPLACES_${PN} = "mplayer-atty"

PV = "0.0+1.0rc3+svnr${SRCPV}"
PR = "r19"
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_angstrom = "1"
DEFAULT_PREFERENCE_shr = "1"

PARALLEL_MAKE = ""

S = "${WORKDIR}/trunk"

PACKAGES =+ "mencoder"

FILES_${PN} = "${bindir}/mplayer ${libdir}"
FILES_mencoder = "${bindir}/mencoder"

inherit autotools pkgconfig

# We want a kernel header for armv7a, but we don't want to make mplayer machine specific for that
STAGING_KERNEL_DIR = "${STAGING_DIR}/${MACHINE_ARCH}${TARGET_VENDOR}-${TARGET_OS}/kernel"

EXTRA_OECONF = " \
	--prefix=/usr \
	--mandir=${mandir} \
	--target=${SIMPLE_TARGET_SYS} \
	\
	--enable-mencoder \
	--disable-gui \
	--enable-largefiles \
	--disable-linux-devfs \
	--disable-lirc \
	--disable-lircc \
	--disable-joystick \
	--disable-vm \
	--disable-xf86keysym \
	--enable-tv \
	--enable-tv-v4l1 \	 
	--enable-tv-v4l2 \
	--disable-tv-bsdbt848 \
	--enable-rtc \
	--enable-network \
	--disable-smb \
	--enable-live \
	--disable-dvdnav \
	--enable-dvdread \
	--disable-dvdread-internal \
	--enable-libdvdcss-internal \
	--disable-cdparanoia \
	--enable-freetype \
	--enable-menu \
	--enable-sortsub \
	--disable-fribidi \
	--disable-enca \
	--disable-ftp \
	--disable-vstream \
	\
	--disable-gif \
	--enable-png \
	--enable-jpeg \
	--disable-libcdio \
	--disable-liblzo \
	--disable-qtx \
	--disable-xanim \
	--disable-real \
	--disable-xvid \
	--disable-x264 \
	\
	--disable-libavutil_so \
	--disable-libavcodec_so \
	--disable-libavformat_so \
	--disable-libpostproc_so \
	\
	--enable-tremor-low \
	\
	--disable-speex \
	--enable-theora \
	--disable-faac \
	--disable-ladspa \
	--disable-libdv \
	--enable-mad \
	--disable-toolame \
	--disable-twolame \
	--disable-xmms \
	--disable-mp3lib \
	--enable-libmpeg2 \
	--disable-musepack \
	\
	--disable-gl \
	--disable-vesa \
	--disable-svga \
	--enable-sdl \
	--disable-aa \
	--disable-caca \
	--disable-ggi \
	--disable-ggiwmh \
	--disable-directx \
	--disable-dxr2 \
	--disable-dxr3 \
	--disable-dvb \
	--enable-dvbhead \
	--disable-mga \
	--disable-xmga \
	--enable-xv \
	--disable-xvmc \
	--disable-vm \
	--disable-xinerama \
	--enable-x11 \
	--enable-fbdev \
	--disable-mlib \
	--disable-3dfx \
	--disable-tdfxfb \
	--disable-s3fb \
	--disable-directfb \
	--disable-zr \
	--disable-bl \
	--disable-tdfxvid \
	--disable-tga \
	--disable-pnm \
	--disable-md5sum \
	\
	--enable-alsa \
	--enable-ossaudio \
	--disable-arts \
	--disable-esd \
	--disable-pulse \
	--disable-jack \
	--disable-openal \
	--disable-nas \
	--disable-sgiaudio \
	--disable-sunaudio \
	--disable-win32waveout \
	--enable-select \
	\
	--extra-libs=' -lBasicUsageEnvironment -lUsageEnvironment -lgroupsock -lliveMedia -lstdc++' \
    --enable-protocol='file_protocol pipe_protocol http_protocol rtmp_protocol tcp_protocol udp_protocol' \
"

EXTRA_OECONF_append_arm = " --disable-decoder=vorbis_decoder \
				--disable-encoder=vorbis_encoder"

EXTRA_OECONF_append_armv6 = " --enable-armv6"
EXTRA_OECONF_append_armv7a = " --enable-armv6 --enable-neon"


#build with support for the iwmmxt instruction and pxa270fb overlay support (pxa270 and up)
#not every iwmmxt machine has the lcd connected to pxafb, but building the module doesn't hurt 
MY_ARCH := "${PACKAGE_ARCH}"
PACKAGE_ARCH = "${@base_contains('MACHINE_FEATURES', 'iwmmxt', 'iwmmxt', '${MY_ARCH}',d)}"

MY_TARGET_CC_ARCH := "${TARGET_CC_ARCH}"
TARGET_CC_ARCH = "${@base_contains('MACHINE_FEATURES', 'iwmmxt', '-march=iwmmxt -mtune=iwmmxt', '${MY_TARGET_CC_ARCH}',d)}"

EXTRA_OECONF_append = " ${@base_contains('MACHINE_FEATURES', 'iwmmxt', ' --enable-iwmmxt', '',d)} "
EXTRA_OECONF_append = " ${@base_contains('MACHINE_FEATURES', 'x86', '--enable-runtime-cpudetection', '',d)} "

FULL_OPTIMIZATION = "-fexpensive-optimizations -fomit-frame-pointer -frename-registers -O4 -ffast-math"
FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations  -ftree-vectorize -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"
# FIXME: Temporarily disable debugging to work-around http://gcc.gnu.org/bugzilla/show_bug.cgi?id=37987
DEBUG_OPTIMIZATION_spitz = "-O -fomit-frame-pointer -g"
DEBUG_OPTIMIZATION_akita = "-O -fomit-frame-pointer -g"

do_configure_prepend_armv7a() {
	cp ${WORKDIR}/yuv.S ${S}/libvo
 	cp ${WORKDIR}/vo_omapfb.c ${S}/libvo
	cp ${STAGING_KERNEL_DIR}/arch/arm/plat-omap/include/mach/omapfb.h ${S}/libvo/omapfb.h || true
 	cp ${STAGING_KERNEL_DIR}/include/asm-arm/arch-omap/omapfb.h ${S}/libvo/omapfb.h || true
	cp ${STAGING_KERNEL_DIR}/include/linux/omapfb.h ${S}/libvo/omapfb.h || true
 	sed -e 's/__user//g' -i ${S}/libvo/omapfb.h || true
}

CFLAGS_append = " -I${S}/libdvdread4 "

do_configure() {
	sed -i 's|/usr/include|${STAGING_INCDIR}|g' ${S}/configure
	sed -i 's|/usr/lib|${STAGING_LIBDIR}|g' ${S}/configure
	sed -i 's|/usr/\S*include[\w/]*||g' ${S}/configure
	sed -i 's|/usr/\S*lib[\w/]*||g' ${S}/configure
	sed -i 's|HOST_CC|BUILD_CC|' ${S}/Makefile

	export SIMPLE_TARGET_SYS="$(echo ${TARGET_SYS} | sed s:${TARGET_VENDOR}::g)"
	./configure ${EXTRA_OECONF}
	
}

do_compile () {
	oe_runmake
}

