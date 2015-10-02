TaskQueue {
	var <queue, <task, <awake = false;
	classvar <stack, <current;


	*new {
		^super.new.init
	}

	*pop {
		if(stack.notNil and: { stack.notEmpty }) { current = stack.pop };
	}

	*push {
		var new = this.new;
		stack = stack.add(new);
		current = new;
	}

	*pushIfNeeded {
		if(current.isNil or: { thisThread != current.task.stream }) { this.push }
	}

	init {
		queue = LinkedList.new;
		task = Task {
			var func, routine;
			while {
				func = queue.popFirst;
				func.notNil
			} {
				func.value // we could add a current subroutine that makes starting/stopping more fine grained.
			}
		};
	}

	play { |clock, doReset, quant|
		awake = true;
		if(this.needsRestart) {
			task.reset.play(clock, doReset, quant)
		}
	}

	stop {
		awake = false;
		task.stop
	}

	add { |func|
		queue.add(func);
		if(this.needsRestart) {
			task.reset.play // todo: check if we need to supply clock, reset, quant
		}
	}

	needsRestart {
		^awake and: { task.isPlaying.not }
	}


}