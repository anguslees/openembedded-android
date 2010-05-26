require linux-libc-headers.inc

DEFAULT_PREFERENCE = "-1"
INHIBIT_DEFAULT_DEPS = "1"
PR = "r1"

SRC_URI = " \
	${KERNELORG_MIRROR}/pub/linux/kernel/people/dwmw2/kernel-headers/snapshot/linux-kernel-headers-2.6.19-rc1.tar.bz2 \
	file://arm-procinfo-hwcap.patch \
	file://arm-unistd-syscall.patch \
	file://linux-err.patch \
"

S = "${WORKDIR}/linux-2.6.19-rc1"

set_arch() {
	case ${TARGET_ARCH} in
		alpha*)   ARCH=alpha ;;
		arm*)     ARCH=arm ;;
		cris*)    ARCH=cris ;;
		hppa*)    ARCH=parisc ;;
		i*86*)    ARCH=i386 ;;
		ia64*)    ARCH=ia64 ;;
		mips*)    ARCH=mips ;;
		m68k*)    ARCH=m68k ;;
		powerpc*) ARCH=powerpc ;;
		s390*)    ARCH=s390 ;;
		sh*)      ARCH=sh ;;
		sparc64*) ARCH=sparc64 ;;
		sparc*)   ARCH=sparc ;;
		x86_64*)  ARCH=x86_64 ;;
	esac
}

do_install() {
	set_arch
	install -d ${D}${includedir}
	cp -pfLR ${S}${includedir}/linux ${D}${includedir}/
	cp -pfLR ${S}${includedir}/asm-${ARCH} ${D}${includedir}/asm
	cp -pfLR ${S}${includedir}/asm-generic ${D}${includedir}/
}

SRC_URI[md5sum] = "f1fc22939d71224923f168ba179b3e51"
SRC_URI[sha256sum] = "35b9045edc5992c2d168fc72ca04a57f4e1c81a0ea987fa88342e54f7c79fbd4"
