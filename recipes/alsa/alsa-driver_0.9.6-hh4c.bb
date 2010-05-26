# needs a gcc4 fix
BROKEN = "1"

COMPATIBLE_MACHINE = "(h3600|h3900)"

DESCRIPTION = "Alsa Drivers"
SECTION = "base"
LICENSE = "GPL"
PR = "r6"

DEPENDS += "fakeroot-native"

SRC_URI = "ftp://ftp.handhelds.org/packages/alsa-driver/alsa-driver-${PV}.tar.gz \
	file://sound.p.patch \
	file://h5400.patch \
	file://sa11xx.patch \
	file://adriver.h.patch"

inherit autotools module

EXTRA_OECONF = "--with-sequencer=yes \
	--with-isapnp=no \
	--with-oss=yes \
	--with-kernel=${STAGING_KERNEL_DIR} \
	--with-kernel-version=${KERNEL_VERSION}"

PACKAGES =+ "${PN}-midi ${PN}-misc"
FILES_${PN} = "/lib/modules/*/misc/snd* \
	${sysconfdir}/modutils/*"
midi_modules = "snd-seq-midi-emul.o \
	snd-seq-midi-event.o \
	snd-seq-midi.o \
	snd-seq-virmidi.o \
	snd-seq-oss.o"
FILES_${PN}-midi = "${@' '.join(map ((lambda x: '/lib/modules/*/misc/%s' % x), bb.data.getVar('midi_modules', d).split()))}"
misc_modules = "snd-gus-synth.o \
	snd-emu8000-synth.o \
	snd-emux-synth.o \
	snd-ainstr-fm.o \
	snd-ainstr-gf1.o \
	snd-ainstr-iw.o \
	snd-ainstr-simple.o"
FILES_${PN}-misc = "${@' '.join(map ((lambda x: '/lib/modules/*/misc/%s' % x), bb.data.getVar('misc_modules', d).split()))}"

# put in-kernel headers first in the include search path.
# without this all configure checks fail
CFLAGS =+ "-I${STAGING_KERNEL_DIR}/include"

do_configure() {

cards=
if egrep "CONFIG_SA1100_H3[168]00=y" "${STAGING_KERNEL_DIR}/.config" ; then
  cards="$cards,sa11xx-uda1341"
  familiar_arch=ipaqsa
fi
if grep "CONFIG_ARCH_H3900=y" "${STAGING_KERNEL_DIR}/.config" ; then
  cards="$cards,pxa-uda1380,h5400-ak4535"
  familiar_arch=ipaqpxa
fi
cards="$cards,bluez-sco,pdaudiocf"

   oe_runconf --with-cards=${cards}
}


do_install() {

if egrep "CONFIG_SA1100_H3[168]00=y" "${STAGING_KERNEL_DIR}/.config" ; then
  familiar_arch=ipaqsa
fi
if grep "CONFIG_ARCH_H3900=y" "${STAGING_KERNEL_DIR}/.config" ; then
  familiar_arch=ipaqpxa
fi

      fakeroot make -k NODEPMOD=yes DESTDIR=${D} install;

      if [ -d ${D}${sysconfdir}/modutils/ ] ; then
         rm -r ${D}${sysconfdir}/modutils/ ;
      fi
      mkdir -p ${D}${sysconfdir}/modutils/
      cp familiar/alsa-modules-${familiar_arch} ${D}${sysconfdir}/modutils/
}

SRC_URI[md5sum] = "de760ca0dda83fa8c79761080a0a655e"
SRC_URI[sha256sum] = "8d9372e06338cc1367eaaab7b5cf3087939c8ab34a89a427308a96d59997c62a"
