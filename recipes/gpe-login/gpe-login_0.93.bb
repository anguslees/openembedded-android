DESCRIPTION = "GPE user login screen"
SECTION = "gpe"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "gtk+ libgpewidget gpe-ownerinfo xkbd"
RDEPENDS_${PN} = "xkbd gpe-theme-clearlooks"
RPROVIDES_${PN} = "gpe-session-starter"
PR = "r2"

SRC_URI_OVERRIDES_PACKAGE_ARCH = "1"

GPE_TARBALL_SUFFIX = "bz2"

inherit gpe autotools pkgconfig


SRC_URI += "file://removeblue-fontsize8.patch"
SRC_URI += " file://chvt-keylaunch.patch "
SRC_URI += " file://c-locale.patch "

SRC_URI_append_spitz = "file://brightness-adjust-keyluanchrc.patch"
SRC_URI_append_akita = "file://brightness-adjust-keyluanchrc.patch"
SRC_URI_append_c7x0 = "file://brightness-adjust-keyluanchrc.patch"


CONFFILES_${PN} += " \
${sysconfdir}/apm/suspend.d/S98lock-display \
${sysconfdir}/gpe/gpe-login.conf \
${sysconfdir}/gpe/locale.alias \
${sysconfdir}/sysconfig/gpelogin \
${sysconfdir}/X11/gpe-login.keylaunchrc \
${sysconfdir}/X11/gpe-login.gtkrc \
${sysconfdir}/X11/gpe-login.setup \
${sysconfdir}/X11/gpe-login.pre-session \
${sysconfdir}/X11/Xinit.d/99gpe-login \
${sysconfdir}/X11/Xsession.d/50autolock \
"

SRC_URI[md5sum] = "cb537b8091f91e5d6776f5435c26d2cf"
SRC_URI[sha256sum] = "6dd6f9b558ef7306578b4be1a83712e1080104570499fa9329072973bd2ed50f"
