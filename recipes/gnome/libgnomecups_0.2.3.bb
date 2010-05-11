DESCRIPTION="Gnome Cups Manager"
LICENSE="GPLv2"

PR ="r0"

DEPENDS="glib-2.0 gtk+ pango cups intltool libgnomeui"

inherit gnome pkgconfig

do_compile_append () {
	cp libgnomecups-1.0.pc libgnomecups-1.0.pc.old
	sed 's:${STAGING_DIR_HOST}::' < libgnomecups-1.0.pc.old > libgnomecups-1.0.pc
}

do_stage() {
	autotools_stage_all
}


SRC_URI[archive.md5sum] = "dc4920c15c9f886f73ea74fbff0ae48b"
SRC_URI[archive.sha256sum] = "e130e80942b386de19a288a4c194ff3dbe9140315b31e982058c8bffbb6a1d29"
