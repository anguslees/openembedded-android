require linux-libc-headers.inc

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native"
PR = "r4"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2 \
	   file://reinstate-a.out.h.patch \
	   file://fix-kernel-conditionalize-a.out.h.patch \
	   file://netfilter-include-types_h-in-userspace.patch \
           file://procinfo.h \
           file://unifdef.patch"

S = "${WORKDIR}/linux-${PV}"

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
	        avr32*)   ARCH=avr32 ;;
                bfin*)    ARCH=blackfin ;;
	esac
}

do_configure() {
	set_arch
	oe_runmake allnoconfig ARCH=$ARCH
}

do_compile () {
}

do_install() {
	set_arch
	oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=$ARCH
}

do_install_append_arm() {
	cp ${WORKDIR}/procinfo.h ${D}${includedir}/asm/
}

SRC_URI[md5sum] = "db95a49a656a3247d4995a797d333153"
SRC_URI[sha256sum] = "108b2a3f2b05c0e57d1d0977619525e46f8d4b425aef4b38b47dcf94292f2dd2"
