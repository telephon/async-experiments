


WaitFor {

	var <>object, <condition;

	*new { |object|
		^super.newCopyArgs(object).init
	}
	init {
		condition = Condition.new;
	}
	hang {
		"hanging on %\n".postf(this.class.name);
		CmdPeriod.add(this);
		condition.hang
	}
	unhang {
		"unhanging from %\n".postf(this.class.name);
		condition.unhang;
		CmdPeriod.remove(this);
	}
	cmdPeriod {
		this.unhang
	}

}


WaitForServer : WaitFor {

	boot {
		object.waitForBoot {
			this.unhang
		};
		this.hang;
	}
}

WaitForMetaGroup : WaitFor {

	new { |target, addAction=\addToHead|
		var synth = object.new(target, addAction);
		OSCFunc({ this.unhang }, "/n_go", synth.server.addr).oneShot;
		this.hang;
		^synth.await // this is special: we return an instance waiter object
	}


}

WaitForMetaSynth : WaitFor {

	new { |defName, args, target, addAction=\addToHead|
		var synth = object.new(defName, args, target, addAction);
		OSCFunc({ this.unhang }, "/n_go", synth.server.addr).oneShot;
		this.hang;
		^synth.await // this is special: we return an instance waiter object
	}

}

WaitForGroup : WaitFor {

}

WaitForSynth : WaitFor {
	release { |releaseTime|
		object.release(releaseTime);
		OSCFunc({ this.unhang }, "/n_end", object.server.addr).oneShot;
		this.hang;
	}
}

WaitForMetaDialog : WaitFor {

	openPanel { arg okFunc, cancelFunc, multipleSelection=false;
		object.openPanel({ this.unhang; okFunc.value }, { this.unhang; cancelFunc.value }, multipleSelection);
		this.hang;
	}
}



+ Object {

	await {
		^this.subclassResponsibility(thisMethod)
	}

}

+ Server {

	await {
		^WaitForServer(this)
	}

}

+ Group {

	*await {
		^WaitForMetaGroup(this)
	}

	await {
		^WaitForGroup(this)
	}
}

+ Synth {

	*await {
		^WaitForMetaSynth(this)
	}

	await {
		^WaitForSynth(this)
	}
}

+ Dialog {

	*await {
		^WaitForMetaDialog(this)
	}

}


//////////////

+ Function {

	async {
		var routine = Routine(this);
		routine.play(AppClock);
		CmdPeriod.doOnce { routine.stop };
	}
}


