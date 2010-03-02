inherit package

BOOTSTRAP_EXTRA_RDEPENDS += "android-sdk"
IMAGE_PKGTYPE ?= "apk"

python package_ipk_fn () {
       from bb import data
       bb.data.setVar('PKGFN', bb.data.getVar('PKG', d), d)
}

python do_package_apk () {
	import re, copy

	workdir = bb.data.getVar('WORKDIR', d, 1)
	if not workdir:
	   bb.error("WORKDIR not defined, unable to package")
	   return

	outdir = bb.data.getVar('DEPLOY_DIR_APK', d, 1)
	if not outdir:
	   bb.error("DEPLOY_DIR_APK not defined, unable to package")
	   return

	dvar = bb.data.getVar('D', d, 1)
	if not dvar:
	   bb.error("D not defined, unable to package")
	   return
	bb.mkdirhier(dvar)

	tmpdir = bb.data.getVar('TMPDIR', d, 1)

	packages = bb.data.getVar('PACKAGES', d, True)
	for pkg in packages.split():
	    localdata = bb.data.createCopy(d)
	    pkgdest = bb.data.getVar('PKGDEST', d, 1)
	    root = "%s/%s" % (pkgdest, pkg)

	    bb.data.setVar('ROOT', '', localdata)
	    bb.data.setVar('ROOT_%s' % pkg, root, localdata)
	    pkgname = bb.data.getVar('PKG_%s' % pkg, localdata, 1)
	    if not pkgname:
	    	    pkgname = pkg
	    bb.data.setVar('PKG', pkgname, localdata)

	    bb.data.update_data(localdata)
	    basedir = os.path.join(os.path.dirname(root))
	    arch = bb.data.getVar('PACKAGE_ARCH', localdata, 1)
	    pkgoutdir = "%s/%s" % (outdir, arch)
	    bb.mkdirhier(pkgoutdir)
	    os.chdir(root)

	    # TODO: write AndroidManifest.xml from other metadata

	    os.chdir(basedir)
	    ret = os.system("PATH=\"%s\" %s" % (bb.data.getVar("PATH", localdata, 1),
	    	  			      	bb.data.getVar("APKBUILDERCMD", d, 1)))
	    if ret != 0:
	       raise bb.build.FuncFailed("apkbuilder execution failed")
}

python () {
       if bb.data.getVar('PACKAGES', d, True) != '':
       	  deps = (bb.data.getVarFlag('do_package_write_apk', 'depends', d) or "").split()
	  deps.append('apk-utils-native:do_populate_staging')
	  bb.data.setVarFlag('do_pakcage_write_apk', 'depends', " ".join(deps), d)
}

python do_package_write_apk () {
       packages = bb.data.getVar('PACKAGES', d, True)
       if not packages:
       	  bb.debug(1, "No PACKAGES defined, nothing to package")
	  return
       bb.build.exec_func("do_package_apk", d)
}
do_package_write_apk[dirs] = "${D}"
addtask package_write_apk before do_package_write after do_package
