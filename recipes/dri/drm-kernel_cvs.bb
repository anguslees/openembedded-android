# FIXME, consider using kernel staging directory instead of KERNEL_SOURCE which is
# located in the work directory.  see modules.bbclass

SECTION = "x11/base"
PR = "r3"
LICENSE = "MIT"

SRC_URI = "${FREEDESKTOP_CVS}/dri;module=drm;method=pserver \
	file://make.patch"

inherit module-base

PV = "0.0+cvs${SRCDATE}"
S = "${WORKDIR}/drm"

do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C linux-core LINUXDIR="${KERNEL_SOURCE}" MAKE="make -e" CC="${KERNEL_CC}" LD="${KERNEL_LD}"
}

do_install() {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/drm
	cd linux-core
	for i in *.ko; do install -m 0644 $i ${D}${base_libdir}/modules/${KERNEL_VERSION}/drm/; done
}

PACKAGES_DYNAMIC = "drm-module-*"

python populate_packages_prepend () {
	root = bb.data.expand('/lib/modules/${KERNEL_VERSION}/drm', d)

        do_split_packages(d, root, '^(.*)\.ko$',
			   output_pattern='drm-module-%s',
			   description='DRM driver module for %s',
			   extra_depends='')
}
